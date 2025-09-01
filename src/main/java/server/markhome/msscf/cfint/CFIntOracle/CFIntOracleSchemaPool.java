// Description: Java 11 implementation of an Oracle CFInt schema pool.

/*
 *	server.markhome.msscf.CFInt
 *
 *	Copyright (c) 2020-2025 Mark Stephen Sobkow
 *	
 *
 *	Manufactured by MSS Code Factory 2.13
 */

package server.markhome.msscf.cfint.CFIntOracle;

import java.util.*;

import org.msscf.msscf.cflib.CFLib.*;
import server.markhome.msscf.cfsec.CFSec.*;
import server.markhome.msscf.cfint.CFInt.*;

public class CFIntOracleSchemaPool
extends CFIntSchemaPool
{
	public CFIntOracleSchemaPool() {
		setJndiName( "java:comp/env/CFINet31ConnectionPool" );
	}

	/**
	 *	You need to overload the implementation of newInstance() to return
	 *	connected instances of your backing store.
	 */
	public ICFIntSchema newInstance() {
		ICFIntSchema inst = new CFIntOracleSchema();
		return( inst );
	}
}
