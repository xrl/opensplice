/*

COPYRIGHT

Copyright 1992, 1993, 1994 Sun Microsystems, Inc.  Printed in the United
States of America.  All Rights Reserved.

this product is protected by copyright and distributed under the following
license restricting its use.

The Interface Definition Language Compiler Front End (CFE) is made
available for your use provided that you include this license and copyright
notice on all media and documentation and the software program in which
this product is incorporated in whole or part. You may copy and extend
functionality (but may not remove functionality) of the Interface
Definition Language CFE without charge, but you are not authorized to
license or distribute it to anyone else except as part of a product or
program developed by you or with the express written consent of Sun
Microsystems, Inc. ("Sun").

The names of Sun Microsystems, Inc. and any of its subsidiaries or
affiliates may not be used in advertising or publicity pertaining to
distribution of Interface Definition Language CFE as permitted herein.

This license is effective until terminated by Sun for failure to comply
with this license.  Upon termination, you shall destroy or return all code
and documentation for the Interface Definition Language CFE.

INTERFACE DEFINITION LANGUAGE CFE IS PROVIDED AS IS WITH NO WARRANTIES OF
ANY KIND INCLUDING THE WARRANTIES OF DESIGN, MERCHANTIBILITY AND FITNESS
FOR A PARTICULAR PURPOSE, NONINFRINGEMENT, OR ARISING FROM A COURSE OF
DEALING, USAGE OR TRADE PRACTICE.

INTERFACE DEFINITION LANGUAGE CFE IS PROVIDED WITH NO SUPPORT AND WITHOUT
ANY OBLIGATION ON THE PART OF Sun OR ANY OF ITS SUBSIDIARIES OR AFFILIATES
TO ASSIST IN ITS USE, CORRECTION, MODIFICATION OR ENHANCEMENT.

SUN OR ANY OF ITS SUBSIDIARIES OR AFFILIATES SHALL HAVE NO LIABILITY WITH
RESPECT TO THE INFRINGEMENT OF COPYRIGHTS, TRADE SECRETS OR ANY PATENTS BY
INTERFACE DEFINITION LANGUAGE CFE OR ANY PART THEREOF.

IN NO EVENT WILL SUN OR ANY OF ITS SUBSIDIARIES OR AFFILIATES BE LIABLE FOR
ANY LOST REVENUE OR PROFITS OR OTHER SPECIAL, INDIRECT AND CONSEQUENTIAL
DAMAGES, EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.

Use, duplication, or disclosure by the government is subject to
restrictions as set forth in subparagraph (c)(1)(ii) of the Rights in
Technical Data and Computer Software clause at DFARS 252.227-7013 and FAR
52.227-19.

Sun, Sun Microsystems and the Sun logo are trademarks or registered
trademarks of Sun Microsystems, Inc.

SunSoft, Inc.
2550 Garcia Avenue
Mountain View, California  94043

NOTE:

SunOS, SunSoft, Sun, Solaris, Sun Microsystems or the Sun logo are
trademarks or registered trademarks of Sun Microsystems, Inc.

 */

/*
** drv_main.cc - Main program for IDL compiler driver
**
** LOGIC:
**
** 1. Initialize compiler driver
** 2. Parse command line args
** 3. If more than one file to parse, fork
** 4. Otherwise, for the single file, invoke DRV_drive
*/

#include "sacpp_DDS_DCPS.h"

#if defined(_WIN32)
#include <process.h>
#endif

#include "idl_narrow.h"
#include "idl.h"
#include "idl_extern.h"
#include "drv_private.h"
#include "drv_link.h"


extern void BE_unlinkAllFiles (); // end-around the abstract "driver", straight
// to the back end

static void DRV_version ()
{
   cerr << idl_global->prog_name()
   << GTDEVEL(", front end version 2.0") << endl;
   (*DRV_BE_version)();
}

/*
** Drive the compilation
**
** LOGIC:
**
** 1. Initialize the CFE, stage 1. This builds the scope stack
** 2. Initialize the BE. This builds an instance of the generator
** 3. Initialize the CFE, stage 2. This builds the global scope
**    and populates it with the predefined types
** 4. Invoke FE_yyparse
** 5. Check for errors from FE_yyparse. If any, exit now
** 6. Check for undefined forward declared interfaces. If any, exit now
** 7. Check if asked to dump AST. If so, do.
** 8. Invoke BE.
*/
int DRV_drive (char *s)
{
   // Pass through CPP
   if (idl_global->compile_flags () & IDL_CF_INFORMATIVE)
      cerr << idl_global->prog_name ()
      << GTDEVEL(": preprocessing ")
      << s
      << "\n";

    idl_global->set_compilation_stage (IDL_GlobalData::CS_PreProcessing);

   DRV_pre_proc (s);
   // Initialize FE stage 1
   idl_global->set_compilation_stage (IDL_GlobalData::CS_InitStage1);

   (*DRV_FE_init_stage1) ();

   // Initialize BE
   idl_global->set_compilation_stage (IDL_GlobalData::CS_BEInit);

   idl_global->set_gen ((*DRV_BE_init) ());

   // Initialize FE stage 2
   idl_global->set_compilation_stage (IDL_GlobalData::CS_InitStage2);

   (*DRV_FE_init_stage2) ();
 
   // Parse
   if (idl_global->compile_flags () & IDL_CF_INFORMATIVE)
      cerr << idl_global->prog_name ()
      << GTDEVEL(": parsing ")
      << s
      << "\n";

   idl_global->set_compilation_stage (IDL_GlobalData::CS_Parsing);

   (*DRV_FE_yyparse) ();

   // If there were any errors, stop
   if (idl_global->err_count () > 0)
   {
      cerr << "\"" << idl_global->prog_name ()
      << "\": "
      << s
      << GTDEVEL (": found ");
      cerr << idl_global->err_count ()
      << GTDEVEL (" error");
      cerr << (idl_global->err_count () > 1 ? GTDEVEL ("s") : "") << "\n";
      // Call BE_abort to allow a BE to clean up after itself
      (*DRV_BE_abort) ();
      return((int) idl_global->err_count ());
   }

   // Dump the code
   if ((idl_global->compile_flags () & IDL_CF_INFORMATIVE)
         && (idl_global->compile_flags () & IDL_CF_DUMP_AST))
      cerr << idl_global->prog_name ()
      << GTDEVEL(": dump ")
      << s
      << "\n";

   if (idl_global->compile_flags () & IDL_CF_DUMP_AST)
   {
      cerr << GTDEVEL ("Dump of AST:\n");
      idl_global->root ()->dump (cerr);
   }

   // Call the main entry point for the BE
   if (idl_global->compile_flags () & IDL_CF_INFORMATIVE)
      cerr << idl_global->prog_name ()
      << GTDEVEL(": BE processing on ")
      << s
      << "\n";

   idl_global->set_compilation_stage (IDL_GlobalData::CS_Producing);

   (*DRV_BE_produce) ();

   // Exit cleanly
   if (idl_global->err_count() > 0)
   {
      BE_unlinkAllFiles ();
   }

    return ((int) idl_global->err_count());
}

// IDL compiler main program. Logic as explained in comment at head of file.
int driver_main(int argc, char **argv)
{
   int ret = 0;

#if defined (_WIN32)
   char * cmdstr = DRV_param_copy (argc, argv);
#endif

   // Open front-end library
   DRV_FE_open ();

   // Initialize driver and global variables
   {
      DRV_init ();
   }

   // Open back-end library
   DRV_BE_open ();

   // Parse arguments
   DRV_parse_args (argc, argv);

   // If a version message is requested, print it and exit
   if (idl_global->compile_flags () & IDL_CF_VERSION)
   {
      DRV_version ();
   }
   else
   {
      // If a usage message is requested, give it and exit
      if (idl_global->compile_flags () & IDL_CF_ONLY_USAGE)
      {
         DRV_usage ();
      }
      else
      {
         // Fork off a process for each file to process. Fork only if
         // there is more than one file to process
         if (DRV_nfiles > 1)
         {
            // DRV_fork never returns
#if !defined (_WIN32)
            DRV_fork ();
#else

            char tmp_command[1024];

            for (int tmpcounter = 0; tmpcounter < DRV_nfiles; tmpcounter++)
            {
               os_sprintf(tmp_command, "%s %s\0", cmdstr, DRV_files[tmpcounter]);

               _flushall ();

               if (system (tmp_command))
               {
                  cerr << "system() failed, errno: " << errno << endl;
               }
            }
#endif
         }
         else
         {
            // Do the one file we have to parse
            // Check if stdin and handle file name appropriately
            if (DRV_nfiles == 0)
            {
               DRV_files[0] = "standard input";
            }

            DRV_file_index = 0;
            ret = DRV_drive (DRV_files[DRV_file_index]);
         }
      }
   }

   return ret; 
}
