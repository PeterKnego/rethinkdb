// Autogenerated by convert_protofile.py.
// Do not edit this file directly.
// The template for this file is located at:
// ../../../../../../../../templates/AstSubclass.java
package com.rethinkdb.ast.gen;

import com.rethinkdb.ast.helper.Arguments;
import com.rethinkdb.ast.helper.OptArgs;
import com.rethinkdb.ast.RqlAst;
import com.rethinkdb.proto.TermType;
import java.util.*;



public class Sunday extends RqlQuery {


    public Sunday(java.lang.Object arg) {
        this(new Arguments(arg), null);
    }
    public Sunday(Arguments args, OptArgs optargs) {
        this(null, args, optargs);
    }
    public Sunday(RqlAst prev, Arguments args, OptArgs optargs) {
        this(prev, TermType.SUNDAY, args, optargs);
    }
    protected Sunday(RqlAst previous, TermType termType, Arguments args, OptArgs optargs){
        super(previous, termType, args, optargs);
    }


    /* Static factories */
    public static Sunday fromArgs(Object... args){
        return new Sunday(new Arguments(args), null);
    }


}