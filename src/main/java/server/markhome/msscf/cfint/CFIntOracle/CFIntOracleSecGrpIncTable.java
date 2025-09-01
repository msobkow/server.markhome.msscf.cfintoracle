// Description: Java 11 Oracle Jdbc DbIO implementation for SecGrpInc.

/*
 *	server.markhome.msscf.CFInt
 *
 *	Copyright (c) 2020-2025 Mark Stephen Sobkow
 *	
 *
 *	Manufactured by MSS Code Factory 2.13
 */

package server.markhome.msscf.cfint.CFIntOracle;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import oracle.jdbc.OracleTypes;
import org.apache.commons.codec.binary.Base64;
import org.msscf.msscf.cflib.CFLib.*;
import server.markhome.msscf.cfsec.CFSec.*;
import server.markhome.msscf.cfint.CFInt.*;
import server.markhome.msscf.cfsec.CFSecObj.*;
import server.markhome.msscf.cfint.CFIntObj.*;

/*
 *	CFIntOracleSecGrpIncTable Oracle Jdbc DbIO implementation
 *	for SecGrpInc.
 */
public class CFIntOracleSecGrpIncTable
	implements ICFIntSecGrpIncTable
{
	private CFIntOracleSchema schema;
	protected PreparedStatement stmtReadBuffByPKey = null;
	protected PreparedStatement stmtLockBuffByPKey = null;
	protected PreparedStatement stmtCreateByPKey = null;
	protected PreparedStatement stmtUpdateByPKey = null;
	protected PreparedStatement stmtDeleteByPKey = null;
	protected PreparedStatement stmtReadAllBuff = null;
	protected PreparedStatement stmtPageAllBuff = null;
	protected PreparedStatement stmtReadBuffByIdIdx = null;
	protected PreparedStatement stmtReadBuffByClusterIdx = null;
	protected PreparedStatement stmtPageBuffByClusterIdx = null;
	protected PreparedStatement stmtReadBuffByGroupIdx = null;
	protected PreparedStatement stmtPageBuffByGroupIdx = null;
	protected PreparedStatement stmtReadBuffByIncludeIdx = null;
	protected PreparedStatement stmtPageBuffByIncludeIdx = null;
	protected PreparedStatement stmtReadBuffByUIncludeIdx = null;
	protected PreparedStatement stmtDeleteByIdIdx = null;
	protected PreparedStatement stmtDeleteByClusterIdx = null;
	protected PreparedStatement stmtDeleteByGroupIdx = null;
	protected PreparedStatement stmtDeleteByIncludeIdx = null;
	protected PreparedStatement stmtDeleteByUIncludeIdx = null;

	public CFIntOracleSecGrpIncTable( CFIntOracleSchema argSchema ) {
		schema = argSchema;
	}

	public void createSecGrpInc( CFSecAuthorization Authorization,
		CFSecSecGrpIncBuff Buff )
	{
		final String S_ProcName = "createSecGrpInc";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		CallableStatement stmtCreateByPKey = null;
		try {
			long ClusterId = Buff.getRequiredClusterId();
			int SecGroupId = Buff.getRequiredSecGroupId();
			int IncludeGroupId = Buff.getRequiredIncludeGroupId();
			Connection cnx = schema.getCnx();
			stmtCreateByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".crt_secinc( ?, ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtCreateByPKey.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtCreateByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtCreateByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtCreateByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtCreateByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtCreateByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtCreateByPKey.setString( argIdx++, "a00e" );
			stmtCreateByPKey.setLong( argIdx++, ClusterId );
			stmtCreateByPKey.setInt( argIdx++, SecGroupId );
			stmtCreateByPKey.setInt( argIdx++, IncludeGroupId );
			stmtCreateByPKey.execute();
			resultSet = (ResultSet)stmtCreateByPKey.getObject( 1 );
			if( resultSet == null ) {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"crt_secinc() did not return a result set" );
			}
			try {
				if( resultSet.next() ) {
					CFSecSecGrpIncBuff createdBuff = unpackSecGrpIncResultSetToBuff( resultSet );
					if( resultSet.next() ) {
						resultSet.last();
						throw new CFLibRuntimeException( getClass(),
							S_ProcName,
							"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
					}
				Buff.setRequiredClusterId( createdBuff.getRequiredClusterId() );
				Buff.setRequiredSecGrpIncId( createdBuff.getRequiredSecGrpIncId() );
				Buff.setRequiredSecGroupId( createdBuff.getRequiredSecGroupId() );
				Buff.setRequiredIncludeGroupId( createdBuff.getRequiredIncludeGroupId() );
				Buff.setRequiredRevision( createdBuff.getRequiredRevision() );
				Buff.setCreatedByUserId( createdBuff.getCreatedByUserId() );
				Buff.setCreatedAt( createdBuff.getCreatedAt() );
				Buff.setUpdatedByUserId( createdBuff.getUpdatedByUserId() );
				Buff.setUpdatedAt( createdBuff.getUpdatedAt() );
				}
				else {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Expected a single-record response, " + resultSet.getRow() + " rows selected" );
				}
			}
			catch( SQLException e ) {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"crt_secinc() did not return a valid result set" );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtCreateByPKey != null ) {
				try {
					stmtCreateByPKey.close();
				}
				catch( SQLException e ) {
				}
				stmtCreateByPKey = null;
			}
		}
	}

	protected static String S_sqlSelectSecGrpIncDistinctClassCode = null;

	public String getSqlSelectSecGrpIncDistinctClassCode() {
		if( S_sqlSelectSecGrpIncDistinctClassCode == null ) {
			S_sqlSelectSecGrpIncDistinctClassCode =
					"SELECT "
				+		"DISTINCT a00e.ClassCode "
				+	"FROM " + schema.getLowerDbSchemaName() + ".SecInc a00e ";
		}
		return( S_sqlSelectSecGrpIncDistinctClassCode );
	}

	protected static String S_sqlSelectSecGrpIncBuff = null;

	public String getSqlSelectSecGrpIncBuff() {
		if( S_sqlSelectSecGrpIncBuff == null ) {
			S_sqlSelectSecGrpIncBuff =
					"SELECT "
				+		"a00e.ClusId, "
				+		"a00e.SecGrpIncId, "
				+		"a00e.SecGrpId, "
				+		"a00e.IncGrpId, "
				+		"a00e.Revision "
				+	"FROM " + schema.getLowerDbSchemaName() + ".SecInc a00e ";
		}
		return( S_sqlSelectSecGrpIncBuff );
	}

	protected CFSecSecGrpIncBuff unpackSecGrpIncResultSetToBuff( ResultSet resultSet )
	throws SQLException
	{
		final String S_ProcName = "unpackSecGrpIncResultSetToBuff";
		int idxcol = 1;
		CFSecSecGrpIncBuff buff = schema.getFactorySecGrpInc().newBuff();
		{
			String colString = resultSet.getString( idxcol );
			if( resultSet.wasNull() ) {
				buff.setCreatedByUserId( null );
			}
			else if( ( colString == null ) || ( colString.length() <= 0 ) ) {
				buff.setCreatedByUserId( null );
			}
			else {
				buff.setCreatedByUserId( UUID.fromString( colString ) );
			}
			idxcol ++;

			colString = resultSet.getString( idxcol );
			if( resultSet.wasNull() ) {
				buff.setCreatedAt( null );
			}
			else if( ( colString == null ) || ( colString.length() <= 0 ) ) {
				buff.setCreatedAt( null );
			}
			else {
				buff.setCreatedAt( CFIntOracleSchema.convertTimestampString( colString ) );
			}
			idxcol++;
			colString = resultSet.getString( idxcol );
			if( resultSet.wasNull() ) {
				buff.setUpdatedByUserId( null );
			}
			else if( ( colString == null ) || ( colString.length() <= 0 ) ) {
				buff.setUpdatedByUserId( null );
			}
			else {
				buff.setUpdatedByUserId( UUID.fromString( colString ) );
			}
			idxcol ++;

			colString = resultSet.getString( idxcol );
			if( resultSet.wasNull() ) {
				buff.setUpdatedAt( null );
			}
			else if( ( colString == null ) || ( colString.length() <= 0 ) ) {
				buff.setUpdatedAt( null );
			}
			else {
				buff.setUpdatedAt( CFIntOracleSchema.convertTimestampString( colString ) );
			}
			idxcol++;
		}
		buff.setRequiredClusterId( resultSet.getLong( idxcol ) );
		idxcol++;
		buff.setRequiredSecGrpIncId( resultSet.getLong( idxcol ) );
		idxcol++;
		buff.setRequiredSecGroupId( resultSet.getInt( idxcol ) );
		idxcol++;
		buff.setRequiredIncludeGroupId( resultSet.getInt( idxcol ) );
		idxcol++;
		buff.setRequiredRevision( resultSet.getInt( idxcol ) );
		return( buff );
	}

	public CFSecSecGrpIncBuff readDerived( CFSecAuthorization Authorization,
		CFSecSecGrpIncPKey PKey )
	{
		final String S_ProcName = "readDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecGrpIncBuff buff;
		buff = readBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecSecGrpIncBuff lockDerived( CFSecAuthorization Authorization,
		CFSecSecGrpIncPKey PKey )
	{
		final String S_ProcName = "lockDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecGrpIncBuff buff;
		buff = lockBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecSecGrpIncBuff[] readAllDerived( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		CFSecSecGrpIncBuff[] buffArray;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buffArray = readAllBuff( Authorization );
		return( buffArray );
	}

	public CFSecSecGrpIncBuff readDerivedByIdIdx( CFSecAuthorization Authorization,
		long ClusterId,
		long SecGrpIncId )
	{
		final String S_ProcName = "CFIntOracleSecGrpIncTable.readDerivedByIdIdx() ";
		CFSecSecGrpIncBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByIdIdx( Authorization,
				ClusterId,
				SecGrpIncId );
		return( buff );
	}

	public CFSecSecGrpIncBuff[] readDerivedByClusterIdx( CFSecAuthorization Authorization,
		long ClusterId )
	{
		final String S_ProcName = "readDerivedByClusterIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecGrpIncBuff[] buffList = readBuffByClusterIdx( Authorization,
				ClusterId );
		return( buffList );

	}

	public CFSecSecGrpIncBuff[] readDerivedByGroupIdx( CFSecAuthorization Authorization,
		long ClusterId,
		int SecGroupId )
	{
		final String S_ProcName = "readDerivedByGroupIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecGrpIncBuff[] buffList = readBuffByGroupIdx( Authorization,
				ClusterId,
				SecGroupId );
		return( buffList );

	}

	public CFSecSecGrpIncBuff[] readDerivedByIncludeIdx( CFSecAuthorization Authorization,
		long ClusterId,
		int IncludeGroupId )
	{
		final String S_ProcName = "readDerivedByIncludeIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecGrpIncBuff[] buffList = readBuffByIncludeIdx( Authorization,
				ClusterId,
				IncludeGroupId );
		return( buffList );

	}

	public CFSecSecGrpIncBuff readDerivedByUIncludeIdx( CFSecAuthorization Authorization,
		long ClusterId,
		int SecGroupId,
		int IncludeGroupId )
	{
		final String S_ProcName = "CFIntOracleSecGrpIncTable.readDerivedByUIncludeIdx() ";
		CFSecSecGrpIncBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByUIncludeIdx( Authorization,
				ClusterId,
				SecGroupId,
				IncludeGroupId );
		return( buff );
	}

	public CFSecSecGrpIncBuff readBuff( CFSecAuthorization Authorization,
		CFSecSecGrpIncPKey PKey )
	{
		final String S_ProcName = "readBuff";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByPKey = null;
		try {
			long ClusterId = PKey.getRequiredClusterId();
			long SecGrpIncId = PKey.getRequiredSecGrpIncId();

			stmtReadBuffByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secinc( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByPKey.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByPKey.setLong( argIdx++, ClusterId );
			stmtReadBuffByPKey.setLong( argIdx++, SecGrpIncId );
			stmtReadBuffByPKey.execute();
			resultSet = (ResultSet)stmtReadBuffByPKey.getObject( 1 );
			if( resultSet == null ) {
				return( null );
			}
			try {
				if( resultSet.next() ) {
					CFSecSecGrpIncBuff buff = unpackSecGrpIncResultSetToBuff( resultSet );
					if( resultSet.next() ) {
						resultSet.last();
						throw new CFLibRuntimeException( getClass(),
							S_ProcName,
							"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
					}
					return( buff );
				}
				else {
					return( null );
				}
			}
			catch( SQLException e ) {
				return( null );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtReadBuffByPKey != null ) {
				try {
					stmtReadBuffByPKey.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByPKey = null;
			}
		}
	}

	public CFSecSecGrpIncBuff lockBuff( CFSecAuthorization Authorization,
		CFSecSecGrpIncPKey PKey )
	{
		final String S_ProcName = "lockBuff";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtLockBuffByPKey = null;
		try {
			long ClusterId = PKey.getRequiredClusterId();
			long SecGrpIncId = PKey.getRequiredSecGrpIncId();

			stmtLockBuffByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".lck_secinc( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtLockBuffByPKey.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtLockBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtLockBuffByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtLockBuffByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtLockBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtLockBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtLockBuffByPKey.setLong( argIdx++, ClusterId );
			stmtLockBuffByPKey.setLong( argIdx++, SecGrpIncId );
			stmtLockBuffByPKey.execute();
			resultSet = (ResultSet)stmtLockBuffByPKey.getObject( 1 );
			if( resultSet == null ) {
				return( null );
			}
			try {
				if( resultSet.next() ) {
					CFSecSecGrpIncBuff buff = unpackSecGrpIncResultSetToBuff( resultSet );
					if( resultSet.next() ) {
						resultSet.last();
						throw new CFLibRuntimeException( getClass(),
							S_ProcName,
							"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
					}
					return( buff );
				}
				else {
					return( null );
				}
			}
			catch( SQLException e ) {
				return( null );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtLockBuffByPKey != null ) {
				try {
					stmtLockBuffByPKey.close();
				}
				catch( SQLException e ) {
				}
				stmtLockBuffByPKey = null;
			}
		}
	}

	public CFSecSecGrpIncBuff[] readAllBuff( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllBuff";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadAllBuff = null;
		try {
			CFSecSecGrpIncBuff buff = null;
			List<CFSecSecGrpIncBuff> buffList = new LinkedList<CFSecSecGrpIncBuff>();
			stmtReadAllBuff = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secincall( ?, ?, ?, ?, ?, ? ) ); end;" );
			int argIdx = 1;
			stmtReadAllBuff.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadAllBuff.execute();
			resultSet = (ResultSet)stmtReadAllBuff.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						buff = unpackSecGrpIncResultSetToBuff( resultSet );
						buffList.add( buff );
					}
				}
				catch( SQLException e ) {
					// Oracle may return an invalid resultSet if the rowset is empty
				}
			}
			int idx = 0;
			CFSecSecGrpIncBuff[] retBuff = new CFSecSecGrpIncBuff[ buffList.size() ];
			Iterator<CFSecSecGrpIncBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtReadAllBuff != null ) {
				try {
					stmtReadAllBuff.close();
				}
				catch( SQLException e ) {
				}
				stmtReadAllBuff = null;
			}
		}
	}

	/**
	 *	Read a page of all the specific SecGrpInc buffer instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecGrpInc instances in the database accessible for the Authorization.
	 */
	public CFSecSecGrpIncBuff[] pageAllBuff( CFSecAuthorization Authorization,
		Long priorClusterId,
		Long priorSecGrpIncId )
	{
		final String S_ProcName = "pageAllBuff";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtPageAllBuff = null;
		try {
			CFSecSecGrpIncBuff buff = null;
			List<CFSecSecGrpIncBuff> buffList = new LinkedList<CFSecSecGrpIncBuff>();
			stmtPageAllBuff = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".pg_secincall( ?, ?, ?, ?, ?, ?, ?, ? ); end;" );
			int argIdx = 1;
			stmtPageAllBuff.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( priorClusterId != null ) {
				stmtPageAllBuff.setLong( argIdx++, priorClusterId.longValue() );
			}
			else {
				stmtPageAllBuff.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			if( priorSecGrpIncId != null ) {
				stmtPageAllBuff.setLong( argIdx++, priorSecGrpIncId.longValue() );
			}
			else {
				stmtPageAllBuff.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			stmtPageAllBuff.execute();
			resultSet = (ResultSet)stmtPageAllBuff.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						buff = unpackSecGrpIncResultSetToBuff( resultSet );
						buffList.add( buff );
					}
				}
				catch( SQLException e ) {
					// Oracle may return an invalid resultSet if the rowset is empty
				}
			}
			int idx = 0;
			CFSecSecGrpIncBuff[] retBuff = new CFSecSecGrpIncBuff[ buffList.size() ];
			Iterator<CFSecSecGrpIncBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtPageAllBuff != null ) {
				try {
					stmtPageAllBuff.close();
				}
				catch( SQLException e ) {
				}
				stmtPageAllBuff = null;
			}
		}
	}

	public CFSecSecGrpIncBuff readBuffByIdIdx( CFSecAuthorization Authorization,
		long ClusterId,
		long SecGrpIncId )
	{
		final String S_ProcName = "readBuffByIdIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByIdIdx = null;
		try {
			stmtReadBuffByIdIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secincbyididx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByIdIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByIdIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByIdIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByIdIdx.setLong( argIdx++, ClusterId );
			stmtReadBuffByIdIdx.setLong( argIdx++, SecGrpIncId );
			stmtReadBuffByIdIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByIdIdx.getObject( 1 );
			if( resultSet == null ) {
				return( null );
			}
			try {
				if( resultSet.next() ) {
					CFSecSecGrpIncBuff buff = unpackSecGrpIncResultSetToBuff( resultSet );
					if( resultSet.next() ) {
						resultSet.last();
						throw new CFLibRuntimeException( getClass(),
							S_ProcName,
							"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
					}
					return( buff );
				}
				else {
					return( null );
				}
			}
			catch( SQLException e ) {
				return( null );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtReadBuffByIdIdx != null ) {
				try {
					stmtReadBuffByIdIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByIdIdx = null;
			}
		}
	}

	public CFSecSecGrpIncBuff[] readBuffByClusterIdx( CFSecAuthorization Authorization,
		long ClusterId )
	{
		final String S_ProcName = "readBuffByClusterIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByClusterIdx = null;
		List<CFSecSecGrpIncBuff> buffList = new LinkedList<CFSecSecGrpIncBuff>();
		try {
			stmtReadBuffByClusterIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secincbyclusteridx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByClusterIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByClusterIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByClusterIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByClusterIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByClusterIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByClusterIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByClusterIdx.setLong( argIdx++, ClusterId );
			stmtReadBuffByClusterIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByClusterIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecSecGrpIncBuff buff = unpackSecGrpIncResultSetToBuff( resultSet );
						buffList.add( buff );
					}
					try {
						resultSet.close();
					}
					catch( SQLException e ) {
					}
					resultSet = null;
				}
				catch( SQLException e ) {
				}
			}
			int idx = 0;
			CFSecSecGrpIncBuff[] retBuff = new CFSecSecGrpIncBuff[ buffList.size() ];
			Iterator<CFSecSecGrpIncBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtReadBuffByClusterIdx != null ) {
				try {
					stmtReadBuffByClusterIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByClusterIdx = null;
			}
		}
	}

	public CFSecSecGrpIncBuff[] readBuffByGroupIdx( CFSecAuthorization Authorization,
		long ClusterId,
		int SecGroupId )
	{
		final String S_ProcName = "readBuffByGroupIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByGroupIdx = null;
		List<CFSecSecGrpIncBuff> buffList = new LinkedList<CFSecSecGrpIncBuff>();
		try {
			stmtReadBuffByGroupIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secincbygroupidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByGroupIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByGroupIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByGroupIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByGroupIdx.setLong( argIdx++, ClusterId );
			stmtReadBuffByGroupIdx.setInt( argIdx++, SecGroupId );
			stmtReadBuffByGroupIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByGroupIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecSecGrpIncBuff buff = unpackSecGrpIncResultSetToBuff( resultSet );
						buffList.add( buff );
					}
					try {
						resultSet.close();
					}
					catch( SQLException e ) {
					}
					resultSet = null;
				}
				catch( SQLException e ) {
				}
			}
			int idx = 0;
			CFSecSecGrpIncBuff[] retBuff = new CFSecSecGrpIncBuff[ buffList.size() ];
			Iterator<CFSecSecGrpIncBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtReadBuffByGroupIdx != null ) {
				try {
					stmtReadBuffByGroupIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByGroupIdx = null;
			}
		}
	}

	public CFSecSecGrpIncBuff[] readBuffByIncludeIdx( CFSecAuthorization Authorization,
		long ClusterId,
		int IncludeGroupId )
	{
		final String S_ProcName = "readBuffByIncludeIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByIncludeIdx = null;
		List<CFSecSecGrpIncBuff> buffList = new LinkedList<CFSecSecGrpIncBuff>();
		try {
			stmtReadBuffByIncludeIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secincbyincludeidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByIncludeIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByIncludeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByIncludeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByIncludeIdx.setLong( argIdx++, ClusterId );
			stmtReadBuffByIncludeIdx.setInt( argIdx++, IncludeGroupId );
			stmtReadBuffByIncludeIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByIncludeIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecSecGrpIncBuff buff = unpackSecGrpIncResultSetToBuff( resultSet );
						buffList.add( buff );
					}
					try {
						resultSet.close();
					}
					catch( SQLException e ) {
					}
					resultSet = null;
				}
				catch( SQLException e ) {
				}
			}
			int idx = 0;
			CFSecSecGrpIncBuff[] retBuff = new CFSecSecGrpIncBuff[ buffList.size() ];
			Iterator<CFSecSecGrpIncBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtReadBuffByIncludeIdx != null ) {
				try {
					stmtReadBuffByIncludeIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByIncludeIdx = null;
			}
		}
	}

	public CFSecSecGrpIncBuff readBuffByUIncludeIdx( CFSecAuthorization Authorization,
		long ClusterId,
		int SecGroupId,
		int IncludeGroupId )
	{
		final String S_ProcName = "readBuffByUIncludeIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByUIncludeIdx = null;
		try {
			stmtReadBuffByUIncludeIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secincbyuincludeidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByUIncludeIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByUIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByUIncludeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByUIncludeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByUIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByUIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByUIncludeIdx.setLong( argIdx++, ClusterId );
			stmtReadBuffByUIncludeIdx.setInt( argIdx++, SecGroupId );
			stmtReadBuffByUIncludeIdx.setInt( argIdx++, IncludeGroupId );
			stmtReadBuffByUIncludeIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByUIncludeIdx.getObject( 1 );
			if( resultSet == null ) {
				return( null );
			}
			try {
				if( resultSet.next() ) {
					CFSecSecGrpIncBuff buff = unpackSecGrpIncResultSetToBuff( resultSet );
					if( resultSet.next() ) {
						resultSet.last();
						throw new CFLibRuntimeException( getClass(),
							S_ProcName,
							"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
					}
					return( buff );
				}
				else {
					return( null );
				}
			}
			catch( SQLException e ) {
				return( null );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtReadBuffByUIncludeIdx != null ) {
				try {
					stmtReadBuffByUIncludeIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByUIncludeIdx = null;
			}
		}
	}

	/**
	 *	Read a page array of the specific SecGrpInc buffer instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argClusterId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecGrpIncBuff[] pageBuffByClusterIdx( CFSecAuthorization Authorization,
		long ClusterId,
		Long priorClusterId,
		Long priorSecGrpIncId )
	{
		final String S_ProcName = "pageBuffByClusterIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtPageBuffByClusterIdx = null;
		List<CFSecSecGrpIncBuff> buffList = new LinkedList<CFSecSecGrpIncBuff>();
		try {
			stmtPageBuffByClusterIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".pg_secincbyclusteridx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtPageBuffByClusterIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtPageBuffByClusterIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByClusterIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByClusterIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByClusterIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByClusterIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtPageBuffByClusterIdx.setLong( argIdx++, ClusterId );
			if( priorClusterId != null ) {
				stmtPageBuffByClusterIdx.setLong( argIdx++, priorClusterId.longValue() );
			}
			else {
				stmtPageBuffByClusterIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			if( priorSecGrpIncId != null ) {
				stmtPageBuffByClusterIdx.setLong( argIdx++, priorSecGrpIncId.longValue() );
			}
			else {
				stmtPageBuffByClusterIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			stmtPageBuffByClusterIdx.execute();
			resultSet = (ResultSet)stmtPageBuffByClusterIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecSecGrpIncBuff buff = unpackSecGrpIncResultSetToBuff( resultSet );
						buffList.add( buff );
					}
					try {
						resultSet.close();
					}
					catch( SQLException e ) {
					}
					resultSet = null;
				}
				catch( SQLException e ) {
				}
			}
			int idx = 0;
			CFSecSecGrpIncBuff[] retBuff = new CFSecSecGrpIncBuff[ buffList.size() ];
			Iterator<CFSecSecGrpIncBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtPageBuffByClusterIdx != null ) {
				try {
					stmtPageBuffByClusterIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtPageBuffByClusterIdx = null;
			}
		}
	}

	/**
	 *	Read a page array of the specific SecGrpInc buffer instances identified by the duplicate key GroupIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argClusterId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	argSecGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecGrpIncBuff[] pageBuffByGroupIdx( CFSecAuthorization Authorization,
		long ClusterId,
		int SecGroupId,
		Long priorClusterId,
		Long priorSecGrpIncId )
	{
		final String S_ProcName = "pageBuffByGroupIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtPageBuffByGroupIdx = null;
		List<CFSecSecGrpIncBuff> buffList = new LinkedList<CFSecSecGrpIncBuff>();
		try {
			stmtPageBuffByGroupIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".pg_secincbygroupidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtPageBuffByGroupIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtPageBuffByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByGroupIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByGroupIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtPageBuffByGroupIdx.setLong( argIdx++, ClusterId );
			stmtPageBuffByGroupIdx.setInt( argIdx++, SecGroupId );
			if( priorClusterId != null ) {
				stmtPageBuffByGroupIdx.setLong( argIdx++, priorClusterId.longValue() );
			}
			else {
				stmtPageBuffByGroupIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			if( priorSecGrpIncId != null ) {
				stmtPageBuffByGroupIdx.setLong( argIdx++, priorSecGrpIncId.longValue() );
			}
			else {
				stmtPageBuffByGroupIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			stmtPageBuffByGroupIdx.execute();
			resultSet = (ResultSet)stmtPageBuffByGroupIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecSecGrpIncBuff buff = unpackSecGrpIncResultSetToBuff( resultSet );
						buffList.add( buff );
					}
					try {
						resultSet.close();
					}
					catch( SQLException e ) {
					}
					resultSet = null;
				}
				catch( SQLException e ) {
				}
			}
			int idx = 0;
			CFSecSecGrpIncBuff[] retBuff = new CFSecSecGrpIncBuff[ buffList.size() ];
			Iterator<CFSecSecGrpIncBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtPageBuffByGroupIdx != null ) {
				try {
					stmtPageBuffByGroupIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtPageBuffByGroupIdx = null;
			}
		}
	}

	/**
	 *	Read a page array of the specific SecGrpInc buffer instances identified by the duplicate key IncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argClusterId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	argIncludeGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecGrpIncBuff[] pageBuffByIncludeIdx( CFSecAuthorization Authorization,
		long ClusterId,
		int IncludeGroupId,
		Long priorClusterId,
		Long priorSecGrpIncId )
	{
		final String S_ProcName = "pageBuffByIncludeIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtPageBuffByIncludeIdx = null;
		List<CFSecSecGrpIncBuff> buffList = new LinkedList<CFSecSecGrpIncBuff>();
		try {
			stmtPageBuffByIncludeIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".pg_secincbyincludeidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtPageBuffByIncludeIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtPageBuffByIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByIncludeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByIncludeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtPageBuffByIncludeIdx.setLong( argIdx++, ClusterId );
			stmtPageBuffByIncludeIdx.setInt( argIdx++, IncludeGroupId );
			if( priorClusterId != null ) {
				stmtPageBuffByIncludeIdx.setLong( argIdx++, priorClusterId.longValue() );
			}
			else {
				stmtPageBuffByIncludeIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			if( priorSecGrpIncId != null ) {
				stmtPageBuffByIncludeIdx.setLong( argIdx++, priorSecGrpIncId.longValue() );
			}
			else {
				stmtPageBuffByIncludeIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			stmtPageBuffByIncludeIdx.execute();
			resultSet = (ResultSet)stmtPageBuffByIncludeIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecSecGrpIncBuff buff = unpackSecGrpIncResultSetToBuff( resultSet );
						buffList.add( buff );
					}
					try {
						resultSet.close();
					}
					catch( SQLException e ) {
					}
					resultSet = null;
				}
				catch( SQLException e ) {
				}
			}
			int idx = 0;
			CFSecSecGrpIncBuff[] retBuff = new CFSecSecGrpIncBuff[ buffList.size() ];
			Iterator<CFSecSecGrpIncBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtPageBuffByIncludeIdx != null ) {
				try {
					stmtPageBuffByIncludeIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtPageBuffByIncludeIdx = null;
			}
		}
	}

	public void updateSecGrpInc( CFSecAuthorization Authorization,
		CFSecSecGrpIncBuff Buff )
	{
		final String S_ProcName = "updateSecGrpInc";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtUpdateByPKey = null;
		List<CFSecSecGrpIncBuff> buffList = new LinkedList<CFSecSecGrpIncBuff>();
		try {			long ClusterId = Buff.getRequiredClusterId();
			long SecGrpIncId = Buff.getRequiredSecGrpIncId();
			int SecGroupId = Buff.getRequiredSecGroupId();
			int IncludeGroupId = Buff.getRequiredIncludeGroupId();
			int Revision = Buff.getRequiredRevision();
			stmtUpdateByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".upd_secinc( ?, ?, ?, ?, ?, ?, ?" + ", "
					+	"?" + ", "
					+	"?" + ", "
					+	"?" + ", "
					+	"?" + ", "
					+	"? ); end;" );
			int argIdx = 1;
			stmtUpdateByPKey.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtUpdateByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtUpdateByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtUpdateByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtUpdateByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtUpdateByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtUpdateByPKey.setString( argIdx++, "a00e" );
			stmtUpdateByPKey.setLong( argIdx++, ClusterId );
			stmtUpdateByPKey.setLong( argIdx++, SecGrpIncId );
			stmtUpdateByPKey.setInt( argIdx++, SecGroupId );
			stmtUpdateByPKey.setInt( argIdx++, IncludeGroupId );
			stmtUpdateByPKey.setInt( argIdx++, Revision );
			stmtUpdateByPKey.execute();
			resultSet = (ResultSet)stmtUpdateByPKey.getObject( 1 );
			if( resultSet != null ) {
				try {
					if( resultSet.next() ) {
						CFSecSecGrpIncBuff updatedBuff = unpackSecGrpIncResultSetToBuff( resultSet );
						if( resultSet.next() ) {
							resultSet.last();
							throw new CFLibRuntimeException( getClass(),
								S_ProcName,
								"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
						}
				Buff.setRequiredSecGroupId( updatedBuff.getRequiredSecGroupId() );
				Buff.setRequiredIncludeGroupId( updatedBuff.getRequiredIncludeGroupId() );
				Buff.setRequiredRevision( updatedBuff.getRequiredRevision() );
					}
					else {
						throw new CFLibRuntimeException( getClass(),
							S_ProcName,
							"Expected a single-record response, " + resultSet.getRow() + " rows selected" );
					}
				}
				catch( SQLException e ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"upd_secinc() did not return a valid result cursor" );
				}
				finally {
					if( resultSet != null ) {
						try {
							resultSet.close();
						}
						catch( SQLException e ) {
						}
						resultSet = null;
					}
				}
			}
			else {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"upd_secinc() did not return a result cursor" );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
			if( stmtUpdateByPKey != null ) {
				try {
					stmtUpdateByPKey.close();
				}
				catch( SQLException e ) {
				}
				stmtUpdateByPKey = null;
			}
		}
	}

	public void deleteSecGrpInc( CFSecAuthorization Authorization,
		CFSecSecGrpIncBuff Buff )
	{
		final String S_ProcName = "deleteSecGrpInc";
		Connection cnx = schema.getCnx();
		CallableStatement stmtDeleteByPKey = null;
		try {
			long ClusterId = Buff.getRequiredClusterId();
			long SecGrpIncId = Buff.getRequiredSecGrpIncId();
			stmtDeleteByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".dl_secinc( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtDeleteByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByPKey.setLong( argIdx++, ClusterId );
			stmtDeleteByPKey.setLong( argIdx++, SecGrpIncId );
			stmtDeleteByPKey.setInt( argIdx++, Buff.getRequiredRevision() );;
			stmtDeleteByPKey.execute();
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( stmtDeleteByPKey != null ) {
				try {
					stmtDeleteByPKey.close();
				}
				catch( SQLException e ) {
				}
				stmtDeleteByPKey = null;
			}
		}
	}

	public void deleteSecGrpIncByIdIdx( CFSecAuthorization Authorization,
		long argClusterId,
		long argSecGrpIncId )
	{
		final String S_ProcName = "deleteSecGrpIncByIdIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_secincbyididx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end";
				if( stmtDeleteByIdIdx == null ) {
					stmtDeleteByIdIdx = cnx.prepareStatement( sql );
				}
				int argIdx = 1;
				stmtDeleteByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByIdIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
				stmtDeleteByIdIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
				stmtDeleteByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
				stmtDeleteByIdIdx.setLong( argIdx++, argClusterId );
				stmtDeleteByIdIdx.setLong( argIdx++, argSecGrpIncId );
				int rowsUpdated = stmtDeleteByIdIdx.executeUpdate();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			finally {
				if( resultSet != null ) {
					try {
						resultSet.close();
					}
					catch( SQLException e ) {
					}
					resultSet = null;
				}
			}
	}

	public void deleteSecGrpIncByIdIdx( CFSecAuthorization Authorization,
		CFSecSecGrpIncPKey argKey )
	{
		deleteSecGrpIncByIdIdx( Authorization,
			argKey.getRequiredClusterId(),
			argKey.getRequiredSecGrpIncId() );
	}

	public void deleteSecGrpIncByClusterIdx( CFSecAuthorization Authorization,
		long argClusterId )
	{
		final String S_ProcName = "deleteSecGrpIncByClusterIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_secincbyclusteridx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " ); end";
				if( stmtDeleteByClusterIdx == null ) {
					stmtDeleteByClusterIdx = cnx.prepareStatement( sql );
				}
				int argIdx = 1;
				stmtDeleteByClusterIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByClusterIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
				stmtDeleteByClusterIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
				stmtDeleteByClusterIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByClusterIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
				stmtDeleteByClusterIdx.setLong( argIdx++, argClusterId );
				int rowsUpdated = stmtDeleteByClusterIdx.executeUpdate();
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
		}
	}

	public void deleteSecGrpIncByClusterIdx( CFSecAuthorization Authorization,
		CFSecSecGrpIncByClusterIdxKey argKey )
	{
		deleteSecGrpIncByClusterIdx( Authorization,
			argKey.getRequiredClusterId() );
	}

	public void deleteSecGrpIncByGroupIdx( CFSecAuthorization Authorization,
		long argClusterId,
		int argSecGroupId )
	{
		final String S_ProcName = "deleteSecGrpIncByGroupIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_secincbygroupidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end";
				if( stmtDeleteByGroupIdx == null ) {
					stmtDeleteByGroupIdx = cnx.prepareStatement( sql );
				}
				int argIdx = 1;
				stmtDeleteByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByGroupIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
				stmtDeleteByGroupIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
				stmtDeleteByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
				stmtDeleteByGroupIdx.setLong( argIdx++, argClusterId );
				stmtDeleteByGroupIdx.setInt( argIdx++, argSecGroupId );
				int rowsUpdated = stmtDeleteByGroupIdx.executeUpdate();
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
		}
	}

	public void deleteSecGrpIncByGroupIdx( CFSecAuthorization Authorization,
		CFSecSecGrpIncByGroupIdxKey argKey )
	{
		deleteSecGrpIncByGroupIdx( Authorization,
			argKey.getRequiredClusterId(),
			argKey.getRequiredSecGroupId() );
	}

	public void deleteSecGrpIncByIncludeIdx( CFSecAuthorization Authorization,
		long argClusterId,
		int argIncludeGroupId )
	{
		final String S_ProcName = "deleteSecGrpIncByIncludeIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_secincbyincludeidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end";
				if( stmtDeleteByIncludeIdx == null ) {
					stmtDeleteByIncludeIdx = cnx.prepareStatement( sql );
				}
				int argIdx = 1;
				stmtDeleteByIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByIncludeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
				stmtDeleteByIncludeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
				stmtDeleteByIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
				stmtDeleteByIncludeIdx.setLong( argIdx++, argClusterId );
				stmtDeleteByIncludeIdx.setInt( argIdx++, argIncludeGroupId );
				int rowsUpdated = stmtDeleteByIncludeIdx.executeUpdate();
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
		}
	}

	public void deleteSecGrpIncByIncludeIdx( CFSecAuthorization Authorization,
		CFSecSecGrpIncByIncludeIdxKey argKey )
	{
		deleteSecGrpIncByIncludeIdx( Authorization,
			argKey.getRequiredClusterId(),
			argKey.getRequiredIncludeGroupId() );
	}

	public void deleteSecGrpIncByUIncludeIdx( CFSecAuthorization Authorization,
		long argClusterId,
		int argSecGroupId,
		int argIncludeGroupId )
	{
		final String S_ProcName = "deleteSecGrpIncByUIncludeIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_secincbyuincludeidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end";
				if( stmtDeleteByUIncludeIdx == null ) {
					stmtDeleteByUIncludeIdx = cnx.prepareStatement( sql );
				}
				int argIdx = 1;
				stmtDeleteByUIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByUIncludeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
				stmtDeleteByUIncludeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
				stmtDeleteByUIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByUIncludeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
				stmtDeleteByUIncludeIdx.setLong( argIdx++, argClusterId );
				stmtDeleteByUIncludeIdx.setInt( argIdx++, argSecGroupId );
				stmtDeleteByUIncludeIdx.setInt( argIdx++, argIncludeGroupId );
				int rowsUpdated = stmtDeleteByUIncludeIdx.executeUpdate();
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
		}
	}

	public void deleteSecGrpIncByUIncludeIdx( CFSecAuthorization Authorization,
		CFSecSecGrpIncByUIncludeIdxKey argKey )
	{
		deleteSecGrpIncByUIncludeIdx( Authorization,
			argKey.getRequiredClusterId(),
			argKey.getRequiredSecGroupId(),
			argKey.getRequiredIncludeGroupId() );
	}

	/**
	 *	Release the prepared statements.
	 *	<p>
	 *	When the schema changes connections, the prepared statements
	 *	have to be released because they contain connection-specific
	 *	information for most databases.
	 */
	public void releasePreparedStatements() {
		final String S_ProcName = "releasePreparedStatements";
		S_sqlSelectSecGrpIncBuff = null;
		S_sqlSelectSecGrpIncDistinctClassCode = null;

		if( stmtReadBuffByPKey != null ) {
			try {
				stmtReadBuffByPKey.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			finally {
				stmtReadBuffByPKey = null;
			}
		}
		if( stmtLockBuffByPKey != null ) {
			try {
				stmtLockBuffByPKey.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			finally {
				stmtLockBuffByPKey = null;
			}
		}
		if( stmtCreateByPKey != null ) {
			try {
				stmtCreateByPKey.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			finally {
				stmtCreateByPKey = null;
			}
		}
		if( stmtUpdateByPKey != null ) {
			try {
				stmtUpdateByPKey.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			finally {
				stmtUpdateByPKey = null;
			}
		}
		if( stmtDeleteByPKey != null ) {
			try {
				stmtDeleteByPKey.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			finally {
				stmtDeleteByPKey = null;
			}
		}
		if( stmtDeleteByIdIdx != null ) {
			try {
				stmtDeleteByIdIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByIdIdx = null;
		}
		if( stmtDeleteByClusterIdx != null ) {
			try {
				stmtDeleteByClusterIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByClusterIdx = null;
		}
		if( stmtDeleteByGroupIdx != null ) {
			try {
				stmtDeleteByGroupIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByGroupIdx = null;
		}
		if( stmtDeleteByIncludeIdx != null ) {
			try {
				stmtDeleteByIncludeIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByIncludeIdx = null;
		}
		if( stmtDeleteByUIncludeIdx != null ) {
			try {
				stmtDeleteByUIncludeIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByUIncludeIdx = null;
		}
		if( stmtReadAllBuff != null ) {
			try {
				stmtReadAllBuff.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			finally {
				stmtReadAllBuff = null;
			}
		}
		if( stmtPageAllBuff != null ) {
			try {
				stmtPageAllBuff.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			finally {
				stmtPageAllBuff = null;
			}
		}
		if( stmtReadBuffByIdIdx != null ) {
			try {
				stmtReadBuffByIdIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtReadBuffByIdIdx = null;
		}
		if( stmtReadBuffByClusterIdx != null ) {
			try {
				stmtReadBuffByClusterIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtReadBuffByClusterIdx = null;
		}
		if( stmtPageBuffByClusterIdx != null ) {
			try {
				stmtPageBuffByClusterIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtPageBuffByClusterIdx = null;
		}
		if( stmtReadBuffByGroupIdx != null ) {
			try {
				stmtReadBuffByGroupIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtReadBuffByGroupIdx = null;
		}
		if( stmtPageBuffByGroupIdx != null ) {
			try {
				stmtPageBuffByGroupIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtPageBuffByGroupIdx = null;
		}
		if( stmtReadBuffByIncludeIdx != null ) {
			try {
				stmtReadBuffByIncludeIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtReadBuffByIncludeIdx = null;
		}
		if( stmtPageBuffByIncludeIdx != null ) {
			try {
				stmtPageBuffByIncludeIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtPageBuffByIncludeIdx = null;
		}
		if( stmtReadBuffByUIncludeIdx != null ) {
			try {
				stmtReadBuffByUIncludeIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtReadBuffByUIncludeIdx = null;
		}
		if( stmtDeleteByIdIdx != null ) {
			try {
				stmtDeleteByIdIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtDeleteByIdIdx = null;
		}
		if( stmtDeleteByClusterIdx != null ) {
			try {
				stmtDeleteByClusterIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtDeleteByClusterIdx = null;
		}
		if( stmtDeleteByGroupIdx != null ) {
			try {
				stmtDeleteByGroupIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtDeleteByGroupIdx = null;
		}
		if( stmtDeleteByIncludeIdx != null ) {
			try {
				stmtDeleteByIncludeIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtDeleteByIncludeIdx = null;
		}
		if( stmtDeleteByUIncludeIdx != null ) {
			try {
				stmtDeleteByUIncludeIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtDeleteByUIncludeIdx = null;
		}
	}
}
