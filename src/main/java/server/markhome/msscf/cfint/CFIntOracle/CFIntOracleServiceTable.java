// Description: Java 11 Oracle Jdbc DbIO implementation for Service.

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
 *	CFIntOracleServiceTable Oracle Jdbc DbIO implementation
 *	for Service.
 */
public class CFIntOracleServiceTable
	implements ICFIntServiceTable
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
	protected PreparedStatement stmtReadBuffByHostIdx = null;
	protected PreparedStatement stmtPageBuffByHostIdx = null;
	protected PreparedStatement stmtReadBuffByTypeIdx = null;
	protected PreparedStatement stmtPageBuffByTypeIdx = null;
	protected PreparedStatement stmtReadBuffByUTypeIdx = null;
	protected PreparedStatement stmtReadBuffByUHostPortIdx = null;
	protected PreparedStatement stmtDeleteByIdIdx = null;
	protected PreparedStatement stmtDeleteByClusterIdx = null;
	protected PreparedStatement stmtDeleteByHostIdx = null;
	protected PreparedStatement stmtDeleteByTypeIdx = null;
	protected PreparedStatement stmtDeleteByUTypeIdx = null;
	protected PreparedStatement stmtDeleteByUHostPortIdx = null;

	public CFIntOracleServiceTable( CFIntOracleSchema argSchema ) {
		schema = argSchema;
	}

	public void createService( CFSecAuthorization Authorization,
		CFSecServiceBuff Buff )
	{
		final String S_ProcName = "createService";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		CallableStatement stmtCreateByPKey = null;
		try {
			long ClusterId = Buff.getRequiredClusterId();
			long HostNodeId = Buff.getRequiredHostNodeId();
			int ServiceTypeId = Buff.getRequiredServiceTypeId();
			short HostPort = Buff.getRequiredHostPort();
			Connection cnx = schema.getCnx();
			stmtCreateByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".crt_hostsvc( ?, ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
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
			stmtCreateByPKey.setString( argIdx++, "a012" );
			stmtCreateByPKey.setLong( argIdx++, ClusterId );
			stmtCreateByPKey.setLong( argIdx++, HostNodeId );
			stmtCreateByPKey.setInt( argIdx++, ServiceTypeId );
			stmtCreateByPKey.setShort( argIdx++, HostPort );
			stmtCreateByPKey.execute();
			resultSet = (ResultSet)stmtCreateByPKey.getObject( 1 );
			if( resultSet == null ) {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"crt_hostsvc() did not return a result set" );
			}
			try {
				if( resultSet.next() ) {
					CFSecServiceBuff createdBuff = unpackServiceResultSetToBuff( resultSet );
					if( resultSet.next() ) {
						resultSet.last();
						throw new CFLibRuntimeException( getClass(),
							S_ProcName,
							"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
					}
				Buff.setRequiredClusterId( createdBuff.getRequiredClusterId() );
				Buff.setRequiredServiceId( createdBuff.getRequiredServiceId() );
				Buff.setRequiredHostNodeId( createdBuff.getRequiredHostNodeId() );
				Buff.setRequiredServiceTypeId( createdBuff.getRequiredServiceTypeId() );
				Buff.setRequiredHostPort( createdBuff.getRequiredHostPort() );
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
					"crt_hostsvc() did not return a valid result set" );
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

	protected static String S_sqlSelectServiceDistinctClassCode = null;

	public String getSqlSelectServiceDistinctClassCode() {
		if( S_sqlSelectServiceDistinctClassCode == null ) {
			S_sqlSelectServiceDistinctClassCode =
					"SELECT "
				+		"DISTINCT a012.ClassCode "
				+	"FROM " + schema.getLowerDbSchemaName() + ".HostSvc a012 ";
		}
		return( S_sqlSelectServiceDistinctClassCode );
	}

	protected static String S_sqlSelectServiceBuff = null;

	public String getSqlSelectServiceBuff() {
		if( S_sqlSelectServiceBuff == null ) {
			S_sqlSelectServiceBuff =
					"SELECT "
				+		"a012.ClusterId, "
				+		"a012.ServiceId, "
				+		"a012.HostNodeId, "
				+		"a012.ServiceTypeId, "
				+		"a012.HostPort, "
				+		"a012.Revision "
				+	"FROM " + schema.getLowerDbSchemaName() + ".HostSvc a012 ";
		}
		return( S_sqlSelectServiceBuff );
	}

	protected CFSecServiceBuff unpackServiceResultSetToBuff( ResultSet resultSet )
	throws SQLException
	{
		final String S_ProcName = "unpackServiceResultSetToBuff";
		int idxcol = 1;
		CFSecServiceBuff buff = schema.getFactoryService().newBuff();
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
		buff.setRequiredServiceId( resultSet.getLong( idxcol ) );
		idxcol++;
		buff.setRequiredHostNodeId( resultSet.getLong( idxcol ) );
		idxcol++;
		buff.setRequiredServiceTypeId( resultSet.getInt( idxcol ) );
		idxcol++;
		buff.setRequiredHostPort( resultSet.getShort( idxcol ) );
		idxcol++;
		buff.setRequiredRevision( resultSet.getInt( idxcol ) );
		return( buff );
	}

	public CFSecServiceBuff readDerived( CFSecAuthorization Authorization,
		CFSecServicePKey PKey )
	{
		final String S_ProcName = "readDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecServiceBuff buff;
		buff = readBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecServiceBuff lockDerived( CFSecAuthorization Authorization,
		CFSecServicePKey PKey )
	{
		final String S_ProcName = "lockDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecServiceBuff buff;
		buff = lockBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecServiceBuff[] readAllDerived( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		CFSecServiceBuff[] buffArray;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buffArray = readAllBuff( Authorization );
		return( buffArray );
	}

	public CFSecServiceBuff readDerivedByIdIdx( CFSecAuthorization Authorization,
		long ClusterId,
		long ServiceId )
	{
		final String S_ProcName = "CFIntOracleServiceTable.readDerivedByIdIdx() ";
		CFSecServiceBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByIdIdx( Authorization,
				ClusterId,
				ServiceId );
		return( buff );
	}

	public CFSecServiceBuff[] readDerivedByClusterIdx( CFSecAuthorization Authorization,
		long ClusterId )
	{
		final String S_ProcName = "readDerivedByClusterIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecServiceBuff[] buffList = readBuffByClusterIdx( Authorization,
				ClusterId );
		return( buffList );

	}

	public CFSecServiceBuff[] readDerivedByHostIdx( CFSecAuthorization Authorization,
		long ClusterId,
		long HostNodeId )
	{
		final String S_ProcName = "readDerivedByHostIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecServiceBuff[] buffList = readBuffByHostIdx( Authorization,
				ClusterId,
				HostNodeId );
		return( buffList );

	}

	public CFSecServiceBuff[] readDerivedByTypeIdx( CFSecAuthorization Authorization,
		int ServiceTypeId )
	{
		final String S_ProcName = "readDerivedByTypeIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecServiceBuff[] buffList = readBuffByTypeIdx( Authorization,
				ServiceTypeId );
		return( buffList );

	}

	public CFSecServiceBuff readDerivedByUTypeIdx( CFSecAuthorization Authorization,
		long ClusterId,
		long HostNodeId,
		int ServiceTypeId )
	{
		final String S_ProcName = "CFIntOracleServiceTable.readDerivedByUTypeIdx() ";
		CFSecServiceBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByUTypeIdx( Authorization,
				ClusterId,
				HostNodeId,
				ServiceTypeId );
		return( buff );
	}

	public CFSecServiceBuff readDerivedByUHostPortIdx( CFSecAuthorization Authorization,
		long ClusterId,
		long HostNodeId,
		short HostPort )
	{
		final String S_ProcName = "CFIntOracleServiceTable.readDerivedByUHostPortIdx() ";
		CFSecServiceBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByUHostPortIdx( Authorization,
				ClusterId,
				HostNodeId,
				HostPort );
		return( buff );
	}

	public CFSecServiceBuff readBuff( CFSecAuthorization Authorization,
		CFSecServicePKey PKey )
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
			long ServiceId = PKey.getRequiredServiceId();

			stmtReadBuffByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_hostsvc( ?, ?, ?, ?, ?, ?" + ", "
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
			stmtReadBuffByPKey.setLong( argIdx++, ServiceId );
			stmtReadBuffByPKey.execute();
			resultSet = (ResultSet)stmtReadBuffByPKey.getObject( 1 );
			if( resultSet == null ) {
				return( null );
			}
			try {
				if( resultSet.next() ) {
					CFSecServiceBuff buff = unpackServiceResultSetToBuff( resultSet );
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

	public CFSecServiceBuff lockBuff( CFSecAuthorization Authorization,
		CFSecServicePKey PKey )
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
			long ServiceId = PKey.getRequiredServiceId();

			stmtLockBuffByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".lck_hostsvc( ?, ?, ?, ?, ?, ?" + ", "
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
			stmtLockBuffByPKey.setLong( argIdx++, ServiceId );
			stmtLockBuffByPKey.execute();
			resultSet = (ResultSet)stmtLockBuffByPKey.getObject( 1 );
			if( resultSet == null ) {
				return( null );
			}
			try {
				if( resultSet.next() ) {
					CFSecServiceBuff buff = unpackServiceResultSetToBuff( resultSet );
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

	public CFSecServiceBuff[] readAllBuff( CFSecAuthorization Authorization ) {
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
			CFSecServiceBuff buff = null;
			List<CFSecServiceBuff> buffList = new LinkedList<CFSecServiceBuff>();
			stmtReadAllBuff = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_hostsvcall( ?, ?, ?, ?, ?, ? ) ); end;" );
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
						buff = unpackServiceResultSetToBuff( resultSet );
						buffList.add( buff );
					}
				}
				catch( SQLException e ) {
					// Oracle may return an invalid resultSet if the rowset is empty
				}
			}
			int idx = 0;
			CFSecServiceBuff[] retBuff = new CFSecServiceBuff[ buffList.size() ];
			Iterator<CFSecServiceBuff> iter = buffList.iterator();
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
	 *	Read a page of all the specific Service buffer instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific Service instances in the database accessible for the Authorization.
	 */
	public CFSecServiceBuff[] pageAllBuff( CFSecAuthorization Authorization,
		Long priorClusterId,
		Long priorServiceId )
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
			CFSecServiceBuff buff = null;
			List<CFSecServiceBuff> buffList = new LinkedList<CFSecServiceBuff>();
			stmtPageAllBuff = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".pg_hostsvcall( ?, ?, ?, ?, ?, ?, ?, ? ); end;" );
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
			if( priorServiceId != null ) {
				stmtPageAllBuff.setLong( argIdx++, priorServiceId.longValue() );
			}
			else {
				stmtPageAllBuff.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			stmtPageAllBuff.execute();
			resultSet = (ResultSet)stmtPageAllBuff.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						buff = unpackServiceResultSetToBuff( resultSet );
						buffList.add( buff );
					}
				}
				catch( SQLException e ) {
					// Oracle may return an invalid resultSet if the rowset is empty
				}
			}
			int idx = 0;
			CFSecServiceBuff[] retBuff = new CFSecServiceBuff[ buffList.size() ];
			Iterator<CFSecServiceBuff> iter = buffList.iterator();
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

	public CFSecServiceBuff readBuffByIdIdx( CFSecAuthorization Authorization,
		long ClusterId,
		long ServiceId )
	{
		final String S_ProcName = "readBuffByIdIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByIdIdx = null;
		try {
			stmtReadBuffByIdIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_hostsvcbyididx( ?, ?, ?, ?, ?, ?" + ", "
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
			stmtReadBuffByIdIdx.setLong( argIdx++, ServiceId );
			stmtReadBuffByIdIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByIdIdx.getObject( 1 );
			if( resultSet == null ) {
				return( null );
			}
			try {
				if( resultSet.next() ) {
					CFSecServiceBuff buff = unpackServiceResultSetToBuff( resultSet );
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

	public CFSecServiceBuff[] readBuffByClusterIdx( CFSecAuthorization Authorization,
		long ClusterId )
	{
		final String S_ProcName = "readBuffByClusterIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByClusterIdx = null;
		List<CFSecServiceBuff> buffList = new LinkedList<CFSecServiceBuff>();
		try {
			stmtReadBuffByClusterIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_hostsvcbyclusteridx( ?, ?, ?, ?, ?, ?" + ", "
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
						CFSecServiceBuff buff = unpackServiceResultSetToBuff( resultSet );
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
			CFSecServiceBuff[] retBuff = new CFSecServiceBuff[ buffList.size() ];
			Iterator<CFSecServiceBuff> iter = buffList.iterator();
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

	public CFSecServiceBuff[] readBuffByHostIdx( CFSecAuthorization Authorization,
		long ClusterId,
		long HostNodeId )
	{
		final String S_ProcName = "readBuffByHostIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByHostIdx = null;
		List<CFSecServiceBuff> buffList = new LinkedList<CFSecServiceBuff>();
		try {
			stmtReadBuffByHostIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_hostsvcbyhostidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByHostIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByHostIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByHostIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByHostIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByHostIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByHostIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByHostIdx.setLong( argIdx++, ClusterId );
			stmtReadBuffByHostIdx.setLong( argIdx++, HostNodeId );
			stmtReadBuffByHostIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByHostIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecServiceBuff buff = unpackServiceResultSetToBuff( resultSet );
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
			CFSecServiceBuff[] retBuff = new CFSecServiceBuff[ buffList.size() ];
			Iterator<CFSecServiceBuff> iter = buffList.iterator();
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
			if( stmtReadBuffByHostIdx != null ) {
				try {
					stmtReadBuffByHostIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByHostIdx = null;
			}
		}
	}

	public CFSecServiceBuff[] readBuffByTypeIdx( CFSecAuthorization Authorization,
		int ServiceTypeId )
	{
		final String S_ProcName = "readBuffByTypeIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByTypeIdx = null;
		List<CFSecServiceBuff> buffList = new LinkedList<CFSecServiceBuff>();
		try {
			stmtReadBuffByTypeIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_hostsvcbytypeidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByTypeIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByTypeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByTypeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByTypeIdx.setInt( argIdx++, ServiceTypeId );
			stmtReadBuffByTypeIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByTypeIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecServiceBuff buff = unpackServiceResultSetToBuff( resultSet );
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
			CFSecServiceBuff[] retBuff = new CFSecServiceBuff[ buffList.size() ];
			Iterator<CFSecServiceBuff> iter = buffList.iterator();
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
			if( stmtReadBuffByTypeIdx != null ) {
				try {
					stmtReadBuffByTypeIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByTypeIdx = null;
			}
		}
	}

	public CFSecServiceBuff readBuffByUTypeIdx( CFSecAuthorization Authorization,
		long ClusterId,
		long HostNodeId,
		int ServiceTypeId )
	{
		final String S_ProcName = "readBuffByUTypeIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByUTypeIdx = null;
		try {
			stmtReadBuffByUTypeIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_hostsvcbyutypeidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByUTypeIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByUTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByUTypeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByUTypeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByUTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByUTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByUTypeIdx.setLong( argIdx++, ClusterId );
			stmtReadBuffByUTypeIdx.setLong( argIdx++, HostNodeId );
			stmtReadBuffByUTypeIdx.setInt( argIdx++, ServiceTypeId );
			stmtReadBuffByUTypeIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByUTypeIdx.getObject( 1 );
			if( resultSet == null ) {
				return( null );
			}
			try {
				if( resultSet.next() ) {
					CFSecServiceBuff buff = unpackServiceResultSetToBuff( resultSet );
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
			if( stmtReadBuffByUTypeIdx != null ) {
				try {
					stmtReadBuffByUTypeIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByUTypeIdx = null;
			}
		}
	}

	public CFSecServiceBuff readBuffByUHostPortIdx( CFSecAuthorization Authorization,
		long ClusterId,
		long HostNodeId,
		short HostPort )
	{
		final String S_ProcName = "readBuffByUHostPortIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByUHostPortIdx = null;
		try {
			stmtReadBuffByUHostPortIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_hostsvcbyuhostportidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByUHostPortIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByUHostPortIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByUHostPortIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByUHostPortIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByUHostPortIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByUHostPortIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByUHostPortIdx.setLong( argIdx++, ClusterId );
			stmtReadBuffByUHostPortIdx.setLong( argIdx++, HostNodeId );
			stmtReadBuffByUHostPortIdx.setShort( argIdx++, HostPort );
			stmtReadBuffByUHostPortIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByUHostPortIdx.getObject( 1 );
			if( resultSet == null ) {
				return( null );
			}
			try {
				if( resultSet.next() ) {
					CFSecServiceBuff buff = unpackServiceResultSetToBuff( resultSet );
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
			if( stmtReadBuffByUHostPortIdx != null ) {
				try {
					stmtReadBuffByUHostPortIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByUHostPortIdx = null;
			}
		}
	}

	/**
	 *	Read a page array of the specific Service buffer instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argClusterId	The Service key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecServiceBuff[] pageBuffByClusterIdx( CFSecAuthorization Authorization,
		long ClusterId,
		Long priorClusterId,
		Long priorServiceId )
	{
		final String S_ProcName = "pageBuffByClusterIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtPageBuffByClusterIdx = null;
		List<CFSecServiceBuff> buffList = new LinkedList<CFSecServiceBuff>();
		try {
			stmtPageBuffByClusterIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".pg_hostsvcbyclusteridx( ?, ?, ?, ?, ?, ?" + ", "
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
			if( priorServiceId != null ) {
				stmtPageBuffByClusterIdx.setLong( argIdx++, priorServiceId.longValue() );
			}
			else {
				stmtPageBuffByClusterIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			stmtPageBuffByClusterIdx.execute();
			resultSet = (ResultSet)stmtPageBuffByClusterIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecServiceBuff buff = unpackServiceResultSetToBuff( resultSet );
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
			CFSecServiceBuff[] retBuff = new CFSecServiceBuff[ buffList.size() ];
			Iterator<CFSecServiceBuff> iter = buffList.iterator();
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
	 *	Read a page array of the specific Service buffer instances identified by the duplicate key HostIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argClusterId	The Service key attribute of the instance generating the id.
	 *
	 *	@param	argHostNodeId	The Service key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecServiceBuff[] pageBuffByHostIdx( CFSecAuthorization Authorization,
		long ClusterId,
		long HostNodeId,
		Long priorClusterId,
		Long priorServiceId )
	{
		final String S_ProcName = "pageBuffByHostIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtPageBuffByHostIdx = null;
		List<CFSecServiceBuff> buffList = new LinkedList<CFSecServiceBuff>();
		try {
			stmtPageBuffByHostIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".pg_hostsvcbyhostidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtPageBuffByHostIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtPageBuffByHostIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByHostIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByHostIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByHostIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByHostIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtPageBuffByHostIdx.setLong( argIdx++, ClusterId );
			stmtPageBuffByHostIdx.setLong( argIdx++, HostNodeId );
			if( priorClusterId != null ) {
				stmtPageBuffByHostIdx.setLong( argIdx++, priorClusterId.longValue() );
			}
			else {
				stmtPageBuffByHostIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			if( priorServiceId != null ) {
				stmtPageBuffByHostIdx.setLong( argIdx++, priorServiceId.longValue() );
			}
			else {
				stmtPageBuffByHostIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			stmtPageBuffByHostIdx.execute();
			resultSet = (ResultSet)stmtPageBuffByHostIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecServiceBuff buff = unpackServiceResultSetToBuff( resultSet );
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
			CFSecServiceBuff[] retBuff = new CFSecServiceBuff[ buffList.size() ];
			Iterator<CFSecServiceBuff> iter = buffList.iterator();
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
			if( stmtPageBuffByHostIdx != null ) {
				try {
					stmtPageBuffByHostIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtPageBuffByHostIdx = null;
			}
		}
	}

	/**
	 *	Read a page array of the specific Service buffer instances identified by the duplicate key TypeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argServiceTypeId	The Service key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecServiceBuff[] pageBuffByTypeIdx( CFSecAuthorization Authorization,
		int ServiceTypeId,
		Long priorClusterId,
		Long priorServiceId )
	{
		final String S_ProcName = "pageBuffByTypeIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtPageBuffByTypeIdx = null;
		List<CFSecServiceBuff> buffList = new LinkedList<CFSecServiceBuff>();
		try {
			stmtPageBuffByTypeIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".pg_hostsvcbytypeidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtPageBuffByTypeIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtPageBuffByTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByTypeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByTypeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtPageBuffByTypeIdx.setInt( argIdx++, ServiceTypeId );
			if( priorClusterId != null ) {
				stmtPageBuffByTypeIdx.setLong( argIdx++, priorClusterId.longValue() );
			}
			else {
				stmtPageBuffByTypeIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			if( priorServiceId != null ) {
				stmtPageBuffByTypeIdx.setLong( argIdx++, priorServiceId.longValue() );
			}
			else {
				stmtPageBuffByTypeIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			stmtPageBuffByTypeIdx.execute();
			resultSet = (ResultSet)stmtPageBuffByTypeIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecServiceBuff buff = unpackServiceResultSetToBuff( resultSet );
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
			CFSecServiceBuff[] retBuff = new CFSecServiceBuff[ buffList.size() ];
			Iterator<CFSecServiceBuff> iter = buffList.iterator();
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
			if( stmtPageBuffByTypeIdx != null ) {
				try {
					stmtPageBuffByTypeIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtPageBuffByTypeIdx = null;
			}
		}
	}

	public void updateService( CFSecAuthorization Authorization,
		CFSecServiceBuff Buff )
	{
		final String S_ProcName = "updateService";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtUpdateByPKey = null;
		List<CFSecServiceBuff> buffList = new LinkedList<CFSecServiceBuff>();
		try {			long ClusterId = Buff.getRequiredClusterId();
			long ServiceId = Buff.getRequiredServiceId();
			long HostNodeId = Buff.getRequiredHostNodeId();
			int ServiceTypeId = Buff.getRequiredServiceTypeId();
			short HostPort = Buff.getRequiredHostPort();
			int Revision = Buff.getRequiredRevision();
			stmtUpdateByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".upd_hostsvc( ?, ?, ?, ?, ?, ?, ?" + ", "
					+	"?" + ", "
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
			stmtUpdateByPKey.setString( argIdx++, "a012" );
			stmtUpdateByPKey.setLong( argIdx++, ClusterId );
			stmtUpdateByPKey.setLong( argIdx++, ServiceId );
			stmtUpdateByPKey.setLong( argIdx++, HostNodeId );
			stmtUpdateByPKey.setInt( argIdx++, ServiceTypeId );
			stmtUpdateByPKey.setShort( argIdx++, HostPort );
			stmtUpdateByPKey.setInt( argIdx++, Revision );
			stmtUpdateByPKey.execute();
			resultSet = (ResultSet)stmtUpdateByPKey.getObject( 1 );
			if( resultSet != null ) {
				try {
					if( resultSet.next() ) {
						CFSecServiceBuff updatedBuff = unpackServiceResultSetToBuff( resultSet );
						if( resultSet.next() ) {
							resultSet.last();
							throw new CFLibRuntimeException( getClass(),
								S_ProcName,
								"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
						}
				Buff.setRequiredHostNodeId( updatedBuff.getRequiredHostNodeId() );
				Buff.setRequiredServiceTypeId( updatedBuff.getRequiredServiceTypeId() );
				Buff.setRequiredHostPort( updatedBuff.getRequiredHostPort() );
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
						"upd_hostsvc() did not return a valid result cursor" );
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
					"upd_hostsvc() did not return a result cursor" );
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

	public void deleteService( CFSecAuthorization Authorization,
		CFSecServiceBuff Buff )
	{
		final String S_ProcName = "deleteService";
		Connection cnx = schema.getCnx();
		CallableStatement stmtDeleteByPKey = null;
		try {
			long ClusterId = Buff.getRequiredClusterId();
			long ServiceId = Buff.getRequiredServiceId();
			stmtDeleteByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".dl_hostsvc( ?, ?, ?, ?, ?" + ", "
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
			stmtDeleteByPKey.setLong( argIdx++, ServiceId );
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

	public void deleteServiceByIdIdx( CFSecAuthorization Authorization,
		long argClusterId,
		long argServiceId )
	{
		final String S_ProcName = "deleteServiceByIdIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_hostsvcbyididx( ?, ?, ?, ?, ?" + ", "
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
				stmtDeleteByIdIdx.setLong( argIdx++, argServiceId );
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

	public void deleteServiceByIdIdx( CFSecAuthorization Authorization,
		CFSecServicePKey argKey )
	{
		deleteServiceByIdIdx( Authorization,
			argKey.getRequiredClusterId(),
			argKey.getRequiredServiceId() );
	}

	public void deleteServiceByClusterIdx( CFSecAuthorization Authorization,
		long argClusterId )
	{
		final String S_ProcName = "deleteServiceByClusterIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_hostsvcbyclusteridx( ?, ?, ?, ?, ?" + ", "
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

	public void deleteServiceByClusterIdx( CFSecAuthorization Authorization,
		CFSecServiceByClusterIdxKey argKey )
	{
		deleteServiceByClusterIdx( Authorization,
			argKey.getRequiredClusterId() );
	}

	public void deleteServiceByHostIdx( CFSecAuthorization Authorization,
		long argClusterId,
		long argHostNodeId )
	{
		final String S_ProcName = "deleteServiceByHostIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_hostsvcbyhostidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end";
				if( stmtDeleteByHostIdx == null ) {
					stmtDeleteByHostIdx = cnx.prepareStatement( sql );
				}
				int argIdx = 1;
				stmtDeleteByHostIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByHostIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
				stmtDeleteByHostIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
				stmtDeleteByHostIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByHostIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
				stmtDeleteByHostIdx.setLong( argIdx++, argClusterId );
				stmtDeleteByHostIdx.setLong( argIdx++, argHostNodeId );
				int rowsUpdated = stmtDeleteByHostIdx.executeUpdate();
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

	public void deleteServiceByHostIdx( CFSecAuthorization Authorization,
		CFSecServiceByHostIdxKey argKey )
	{
		deleteServiceByHostIdx( Authorization,
			argKey.getRequiredClusterId(),
			argKey.getRequiredHostNodeId() );
	}

	public void deleteServiceByTypeIdx( CFSecAuthorization Authorization,
		int argServiceTypeId )
	{
		final String S_ProcName = "deleteServiceByTypeIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_hostsvcbytypeidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " ); end";
				if( stmtDeleteByTypeIdx == null ) {
					stmtDeleteByTypeIdx = cnx.prepareStatement( sql );
				}
				int argIdx = 1;
				stmtDeleteByTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByTypeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
				stmtDeleteByTypeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
				stmtDeleteByTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
				stmtDeleteByTypeIdx.setInt( argIdx++, argServiceTypeId );
				int rowsUpdated = stmtDeleteByTypeIdx.executeUpdate();
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

	public void deleteServiceByTypeIdx( CFSecAuthorization Authorization,
		CFSecServiceByTypeIdxKey argKey )
	{
		deleteServiceByTypeIdx( Authorization,
			argKey.getRequiredServiceTypeId() );
	}

	public void deleteServiceByUTypeIdx( CFSecAuthorization Authorization,
		long argClusterId,
		long argHostNodeId,
		int argServiceTypeId )
	{
		final String S_ProcName = "deleteServiceByUTypeIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_hostsvcbyutypeidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end";
				if( stmtDeleteByUTypeIdx == null ) {
					stmtDeleteByUTypeIdx = cnx.prepareStatement( sql );
				}
				int argIdx = 1;
				stmtDeleteByUTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByUTypeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
				stmtDeleteByUTypeIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
				stmtDeleteByUTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByUTypeIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
				stmtDeleteByUTypeIdx.setLong( argIdx++, argClusterId );
				stmtDeleteByUTypeIdx.setLong( argIdx++, argHostNodeId );
				stmtDeleteByUTypeIdx.setInt( argIdx++, argServiceTypeId );
				int rowsUpdated = stmtDeleteByUTypeIdx.executeUpdate();
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

	public void deleteServiceByUTypeIdx( CFSecAuthorization Authorization,
		CFSecServiceByUTypeIdxKey argKey )
	{
		deleteServiceByUTypeIdx( Authorization,
			argKey.getRequiredClusterId(),
			argKey.getRequiredHostNodeId(),
			argKey.getRequiredServiceTypeId() );
	}

	public void deleteServiceByUHostPortIdx( CFSecAuthorization Authorization,
		long argClusterId,
		long argHostNodeId,
		short argHostPort )
	{
		final String S_ProcName = "deleteServiceByUHostPortIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_hostsvcbyuhostportidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end";
				if( stmtDeleteByUHostPortIdx == null ) {
					stmtDeleteByUHostPortIdx = cnx.prepareStatement( sql );
				}
				int argIdx = 1;
				stmtDeleteByUHostPortIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByUHostPortIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
				stmtDeleteByUHostPortIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
				stmtDeleteByUHostPortIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByUHostPortIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
				stmtDeleteByUHostPortIdx.setLong( argIdx++, argClusterId );
				stmtDeleteByUHostPortIdx.setLong( argIdx++, argHostNodeId );
				stmtDeleteByUHostPortIdx.setShort( argIdx++, argHostPort );
				int rowsUpdated = stmtDeleteByUHostPortIdx.executeUpdate();
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

	public void deleteServiceByUHostPortIdx( CFSecAuthorization Authorization,
		CFSecServiceByUHostPortIdxKey argKey )
	{
		deleteServiceByUHostPortIdx( Authorization,
			argKey.getRequiredClusterId(),
			argKey.getRequiredHostNodeId(),
			argKey.getRequiredHostPort() );
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
		S_sqlSelectServiceBuff = null;
		S_sqlSelectServiceDistinctClassCode = null;

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
		if( stmtDeleteByHostIdx != null ) {
			try {
				stmtDeleteByHostIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByHostIdx = null;
		}
		if( stmtDeleteByTypeIdx != null ) {
			try {
				stmtDeleteByTypeIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByTypeIdx = null;
		}
		if( stmtDeleteByUTypeIdx != null ) {
			try {
				stmtDeleteByUTypeIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByUTypeIdx = null;
		}
		if( stmtDeleteByUHostPortIdx != null ) {
			try {
				stmtDeleteByUHostPortIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByUHostPortIdx = null;
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
		if( stmtReadBuffByHostIdx != null ) {
			try {
				stmtReadBuffByHostIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtReadBuffByHostIdx = null;
		}
		if( stmtPageBuffByHostIdx != null ) {
			try {
				stmtPageBuffByHostIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtPageBuffByHostIdx = null;
		}
		if( stmtReadBuffByTypeIdx != null ) {
			try {
				stmtReadBuffByTypeIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtReadBuffByTypeIdx = null;
		}
		if( stmtPageBuffByTypeIdx != null ) {
			try {
				stmtPageBuffByTypeIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtPageBuffByTypeIdx = null;
		}
		if( stmtReadBuffByUTypeIdx != null ) {
			try {
				stmtReadBuffByUTypeIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtReadBuffByUTypeIdx = null;
		}
		if( stmtReadBuffByUHostPortIdx != null ) {
			try {
				stmtReadBuffByUHostPortIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtReadBuffByUHostPortIdx = null;
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
		if( stmtDeleteByHostIdx != null ) {
			try {
				stmtDeleteByHostIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtDeleteByHostIdx = null;
		}
		if( stmtDeleteByTypeIdx != null ) {
			try {
				stmtDeleteByTypeIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtDeleteByTypeIdx = null;
		}
		if( stmtDeleteByUTypeIdx != null ) {
			try {
				stmtDeleteByUTypeIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtDeleteByUTypeIdx = null;
		}
		if( stmtDeleteByUHostPortIdx != null ) {
			try {
				stmtDeleteByUHostPortIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtDeleteByUHostPortIdx = null;
		}
	}
}
