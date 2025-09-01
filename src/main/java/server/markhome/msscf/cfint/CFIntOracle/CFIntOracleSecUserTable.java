// Description: Java 11 Oracle Jdbc DbIO implementation for SecUser.

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
 *	CFIntOracleSecUserTable Oracle Jdbc DbIO implementation
 *	for SecUser.
 */
public class CFIntOracleSecUserTable
	implements ICFIntSecUserTable
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
	protected PreparedStatement stmtReadBuffByULoginIdx = null;
	protected PreparedStatement stmtReadBuffByEMConfIdx = null;
	protected PreparedStatement stmtPageBuffByEMConfIdx = null;
	protected PreparedStatement stmtReadBuffByPwdResetIdx = null;
	protected PreparedStatement stmtPageBuffByPwdResetIdx = null;
	protected PreparedStatement stmtReadBuffByDefDevIdx = null;
	protected PreparedStatement stmtPageBuffByDefDevIdx = null;
	protected PreparedStatement stmtDeleteByIdIdx = null;
	protected PreparedStatement stmtDeleteByULoginIdx = null;
	protected PreparedStatement stmtDeleteByEMConfIdx = null;
	protected PreparedStatement stmtDeleteByPwdResetIdx = null;
	protected PreparedStatement stmtDeleteByDefDevIdx = null;

	public CFIntOracleSecUserTable( CFIntOracleSchema argSchema ) {
		schema = argSchema;
	}

	public void createSecUser( CFSecAuthorization Authorization,
		CFSecSecUserBuff Buff )
	{
		final String S_ProcName = "createSecUser";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		CallableStatement stmtCreateByPKey = null;
		try {
			String LoginId = Buff.getRequiredLoginId();
			String EMailAddress = Buff.getRequiredEMailAddress();
			UUID EMailConfirmUuid = Buff.getOptionalEMailConfirmUuid();
			UUID DfltDevUserId = Buff.getOptionalDfltDevUserId();
			String DfltDevName = Buff.getOptionalDfltDevName();
			String PasswordHash = Buff.getRequiredPasswordHash();
			UUID PasswordResetUuid = Buff.getOptionalPasswordResetUuid();

			UUID SecUserId = UUID.randomUUID();			Connection cnx = schema.getCnx();
			stmtCreateByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".crt_secuser( ?, ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + ", "
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
			stmtCreateByPKey.setString( argIdx++, "a011" );
			stmtCreateByPKey.setString( argIdx++, SecUserId.toString() );
			stmtCreateByPKey.setString( argIdx++, LoginId );
			stmtCreateByPKey.setString( argIdx++, EMailAddress );
			if( EMailConfirmUuid != null ) {
				stmtCreateByPKey.setString( argIdx++, EMailConfirmUuid.toString() );
			}
			else {
				stmtCreateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( DfltDevUserId != null ) {
				stmtCreateByPKey.setString( argIdx++, DfltDevUserId.toString() );
			}
			else {
				stmtCreateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( DfltDevName != null ) {
				stmtCreateByPKey.setString( argIdx++, DfltDevName );
			}
			else {
				stmtCreateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtCreateByPKey.setString( argIdx++, PasswordHash );
			if( PasswordResetUuid != null ) {
				stmtCreateByPKey.setString( argIdx++, PasswordResetUuid.toString() );
			}
			else {
				stmtCreateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtCreateByPKey.execute();
			resultSet = (ResultSet)stmtCreateByPKey.getObject( 1 );
			if( resultSet == null ) {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"crt_secuser() did not return a result set" );
			}
			try {
				if( resultSet.next() ) {
					CFSecSecUserBuff createdBuff = unpackSecUserResultSetToBuff( resultSet );
					if( resultSet.next() ) {
						resultSet.last();
						throw new CFLibRuntimeException( getClass(),
							S_ProcName,
							"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
					}
				Buff.setRequiredSecUserId( createdBuff.getRequiredSecUserId() );
				Buff.setRequiredLoginId( createdBuff.getRequiredLoginId() );
				Buff.setRequiredEMailAddress( createdBuff.getRequiredEMailAddress() );
				Buff.setOptionalEMailConfirmUuid( createdBuff.getOptionalEMailConfirmUuid() );
				Buff.setOptionalDfltDevUserId( createdBuff.getOptionalDfltDevUserId() );
				Buff.setOptionalDfltDevName( createdBuff.getOptionalDfltDevName() );
				Buff.setRequiredPasswordHash( createdBuff.getRequiredPasswordHash() );
				Buff.setOptionalPasswordResetUuid( createdBuff.getOptionalPasswordResetUuid() );
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
					"crt_secuser() did not return a valid result set" );
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

	protected static String S_sqlSelectSecUserDistinctClassCode = null;

	public String getSqlSelectSecUserDistinctClassCode() {
		if( S_sqlSelectSecUserDistinctClassCode == null ) {
			S_sqlSelectSecUserDistinctClassCode =
					"SELECT "
				+		"DISTINCT a011.ClassCode "
				+	"FROM " + schema.getLowerDbSchemaName() + ".SecUser a011 ";
		}
		return( S_sqlSelectSecUserDistinctClassCode );
	}

	protected static String S_sqlSelectSecUserBuff = null;

	public String getSqlSelectSecUserBuff() {
		if( S_sqlSelectSecUserBuff == null ) {
			S_sqlSelectSecUserBuff =
					"SELECT "
				+		"a011.SecUserId, "
				+		"a011.login_id, "
				+		"a011.email_addr, "
				+		"a011.em_confuuid, "
				+		"a011.DefDevUserId, "
				+		"a011.DefDevName, "
				+		"a011.pwd_hash, "
				+		"a011.pwdrstuuid, "
				+		"a011.Revision "
				+	"FROM " + schema.getLowerDbSchemaName() + ".SecUser a011 ";
		}
		return( S_sqlSelectSecUserBuff );
	}

	protected CFSecSecUserBuff unpackSecUserResultSetToBuff( ResultSet resultSet )
	throws SQLException
	{
		final String S_ProcName = "unpackSecUserResultSetToBuff";
		int idxcol = 1;
		CFSecSecUserBuff buff = schema.getFactorySecUser().newBuff();
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
		buff.setRequiredSecUserId( CFIntOracleSchema.convertUuidString( resultSet.getString( idxcol ) ) );
		idxcol++;
		buff.setRequiredLoginId( resultSet.getString( idxcol ) );
		idxcol++;
		buff.setRequiredEMailAddress( resultSet.getString( idxcol ) );
		idxcol++;
		{
			UUID colVal = CFIntOracleSchema.convertUuidString( resultSet.getString( idxcol ) );
			if( resultSet.wasNull() ) {
				buff.setOptionalEMailConfirmUuid( null );
			}
			else {
				buff.setOptionalEMailConfirmUuid( colVal );
			}
		}
		idxcol++;
		{
			UUID colVal = CFIntOracleSchema.convertUuidString( resultSet.getString( idxcol ) );
			if( resultSet.wasNull() ) {
				buff.setOptionalDfltDevUserId( null );
			}
			else {
				buff.setOptionalDfltDevUserId( colVal );
			}
		}
		idxcol++;
		{
			String colVal = resultSet.getString( idxcol );
			if( resultSet.wasNull() ) {
				buff.setOptionalDfltDevName( null );
			}
			else {
				buff.setOptionalDfltDevName( colVal );
			}
		}
		idxcol++;
		buff.setRequiredPasswordHash( resultSet.getString( idxcol ) );
		idxcol++;
		{
			UUID colVal = CFIntOracleSchema.convertUuidString( resultSet.getString( idxcol ) );
			if( resultSet.wasNull() ) {
				buff.setOptionalPasswordResetUuid( null );
			}
			else {
				buff.setOptionalPasswordResetUuid( colVal );
			}
		}
		idxcol++;
		buff.setRequiredRevision( resultSet.getInt( idxcol ) );
		return( buff );
	}

	public CFSecSecUserBuff readDerived( CFSecAuthorization Authorization,
		CFSecSecUserPKey PKey )
	{
		final String S_ProcName = "readDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecUserBuff buff;
		buff = readBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecSecUserBuff lockDerived( CFSecAuthorization Authorization,
		CFSecSecUserPKey PKey )
	{
		final String S_ProcName = "lockDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecUserBuff buff;
		buff = lockBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecSecUserBuff[] readAllDerived( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		CFSecSecUserBuff[] buffArray;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buffArray = readAllBuff( Authorization );
		return( buffArray );
	}

	public CFSecSecUserBuff readDerivedByIdIdx( CFSecAuthorization Authorization,
		UUID SecUserId )
	{
		final String S_ProcName = "CFIntOracleSecUserTable.readDerivedByIdIdx() ";
		CFSecSecUserBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByIdIdx( Authorization,
				SecUserId );
		return( buff );
	}

	public CFSecSecUserBuff readDerivedByULoginIdx( CFSecAuthorization Authorization,
		String LoginId )
	{
		final String S_ProcName = "CFIntOracleSecUserTable.readDerivedByULoginIdx() ";
		CFSecSecUserBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByULoginIdx( Authorization,
				LoginId );
		return( buff );
	}

	public CFSecSecUserBuff[] readDerivedByEMConfIdx( CFSecAuthorization Authorization,
		UUID EMailConfirmUuid )
	{
		final String S_ProcName = "readDerivedByEMConfIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecUserBuff[] buffList = readBuffByEMConfIdx( Authorization,
				EMailConfirmUuid );
		return( buffList );

	}

	public CFSecSecUserBuff[] readDerivedByPwdResetIdx( CFSecAuthorization Authorization,
		UUID PasswordResetUuid )
	{
		final String S_ProcName = "readDerivedByPwdResetIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecUserBuff[] buffList = readBuffByPwdResetIdx( Authorization,
				PasswordResetUuid );
		return( buffList );

	}

	public CFSecSecUserBuff[] readDerivedByDefDevIdx( CFSecAuthorization Authorization,
		UUID DfltDevUserId,
		String DfltDevName )
	{
		final String S_ProcName = "readDerivedByDefDevIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecUserBuff[] buffList = readBuffByDefDevIdx( Authorization,
				DfltDevUserId,
				DfltDevName );
		return( buffList );

	}

	public CFSecSecUserBuff readBuff( CFSecAuthorization Authorization,
		CFSecSecUserPKey PKey )
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
			UUID SecUserId = PKey.getRequiredSecUserId();

			stmtReadBuffByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secuser( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByPKey.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByPKey.setString( argIdx++, SecUserId.toString() );
			stmtReadBuffByPKey.execute();
			resultSet = (ResultSet)stmtReadBuffByPKey.getObject( 1 );
			if( resultSet == null ) {
				return( null );
			}
			try {
				if( resultSet.next() ) {
					CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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

	public CFSecSecUserBuff lockBuff( CFSecAuthorization Authorization,
		CFSecSecUserPKey PKey )
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
			UUID SecUserId = PKey.getRequiredSecUserId();

			stmtLockBuffByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".lck_secuser( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtLockBuffByPKey.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtLockBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtLockBuffByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtLockBuffByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtLockBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtLockBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtLockBuffByPKey.setString( argIdx++, SecUserId.toString() );
			stmtLockBuffByPKey.execute();
			resultSet = (ResultSet)stmtLockBuffByPKey.getObject( 1 );
			if( resultSet == null ) {
				return( null );
			}
			try {
				if( resultSet.next() ) {
					CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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

	public CFSecSecUserBuff[] readAllBuff( CFSecAuthorization Authorization ) {
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
			CFSecSecUserBuff buff = null;
			List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
			stmtReadAllBuff = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secuserall( ?, ?, ?, ?, ?, ? ) ); end;" );
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
						buff = unpackSecUserResultSetToBuff( resultSet );
						buffList.add( buff );
					}
				}
				catch( SQLException e ) {
					// Oracle may return an invalid resultSet if the rowset is empty
				}
			}
			int idx = 0;
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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
	 *	Read a page of all the specific SecUser buffer instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecUser instances in the database accessible for the Authorization.
	 */
	public CFSecSecUserBuff[] pageAllBuff( CFSecAuthorization Authorization,
		UUID priorSecUserId )
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
			CFSecSecUserBuff buff = null;
			List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
			stmtPageAllBuff = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".pg_secuserall( ?, ?, ?, ?, ?, ?, ? ); end;" );
			int argIdx = 1;
			stmtPageAllBuff.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( priorSecUserId != null ) {
				stmtPageAllBuff.setString( argIdx++, priorSecUserId.toString() );
			}
			else {
				stmtPageAllBuff.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtPageAllBuff.execute();
			resultSet = (ResultSet)stmtPageAllBuff.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						buff = unpackSecUserResultSetToBuff( resultSet );
						buffList.add( buff );
					}
				}
				catch( SQLException e ) {
					// Oracle may return an invalid resultSet if the rowset is empty
				}
			}
			int idx = 0;
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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

	public CFSecSecUserBuff readBuffByIdIdx( CFSecAuthorization Authorization,
		UUID SecUserId )
	{
		final String S_ProcName = "readBuffByIdIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByIdIdx = null;
		try {
			stmtReadBuffByIdIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secuserbyididx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByIdIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByIdIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByIdIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByIdIdx.setString( argIdx++, SecUserId.toString() );
			stmtReadBuffByIdIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByIdIdx.getObject( 1 );
			if( resultSet == null ) {
				return( null );
			}
			try {
				if( resultSet.next() ) {
					CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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

	public CFSecSecUserBuff readBuffByULoginIdx( CFSecAuthorization Authorization,
		String LoginId )
	{
		final String S_ProcName = "readBuffByULoginIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByULoginIdx = null;
		try {
			stmtReadBuffByULoginIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secuserbyuloginidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByULoginIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByULoginIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByULoginIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByULoginIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByULoginIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByULoginIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByULoginIdx.setString( argIdx++, LoginId );
			stmtReadBuffByULoginIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByULoginIdx.getObject( 1 );
			if( resultSet == null ) {
				return( null );
			}
			try {
				if( resultSet.next() ) {
					CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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
			if( stmtReadBuffByULoginIdx != null ) {
				try {
					stmtReadBuffByULoginIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByULoginIdx = null;
			}
		}
	}

	public CFSecSecUserBuff[] readBuffByEMConfIdx( CFSecAuthorization Authorization,
		UUID EMailConfirmUuid )
	{
		final String S_ProcName = "readBuffByEMConfIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByEMConfIdx = null;
		List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
		try {
			stmtReadBuffByEMConfIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secuserbyemconfidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByEMConfIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByEMConfIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByEMConfIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( EMailConfirmUuid != null ) {
				stmtReadBuffByEMConfIdx.setString( argIdx++, EMailConfirmUuid.toString() );
			}
			else {
				stmtReadBuffByEMConfIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtReadBuffByEMConfIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByEMConfIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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
			if( stmtReadBuffByEMConfIdx != null ) {
				try {
					stmtReadBuffByEMConfIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByEMConfIdx = null;
			}
		}
	}

	public CFSecSecUserBuff[] readBuffByPwdResetIdx( CFSecAuthorization Authorization,
		UUID PasswordResetUuid )
	{
		final String S_ProcName = "readBuffByPwdResetIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByPwdResetIdx = null;
		List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
		try {
			stmtReadBuffByPwdResetIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secuserbypwdresetidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByPwdResetIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByPwdResetIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByPwdResetIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( PasswordResetUuid != null ) {
				stmtReadBuffByPwdResetIdx.setString( argIdx++, PasswordResetUuid.toString() );
			}
			else {
				stmtReadBuffByPwdResetIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtReadBuffByPwdResetIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByPwdResetIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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
			if( stmtReadBuffByPwdResetIdx != null ) {
				try {
					stmtReadBuffByPwdResetIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByPwdResetIdx = null;
			}
		}
	}

	public CFSecSecUserBuff[] readBuffByDefDevIdx( CFSecAuthorization Authorization,
		UUID DfltDevUserId,
		String DfltDevName )
	{
		final String S_ProcName = "readBuffByDefDevIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtReadBuffByDefDevIdx = null;
		List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
		try {
			stmtReadBuffByDefDevIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".rd_secuserbydefdevidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtReadBuffByDefDevIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtReadBuffByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByDefDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByDefDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( DfltDevUserId != null ) {
				stmtReadBuffByDefDevIdx.setString( argIdx++, DfltDevUserId.toString() );
			}
			else {
				stmtReadBuffByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( DfltDevName != null ) {
				stmtReadBuffByDefDevIdx.setString( argIdx++, DfltDevName );
			}
			else {
				stmtReadBuffByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtReadBuffByDefDevIdx.execute();
			resultSet = (ResultSet)stmtReadBuffByDefDevIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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
			if( stmtReadBuffByDefDevIdx != null ) {
				try {
					stmtReadBuffByDefDevIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtReadBuffByDefDevIdx = null;
			}
		}
	}

	/**
	 *	Read a page array of the specific SecUser buffer instances identified by the duplicate key EMConfIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argEMailConfirmUuid	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecUserBuff[] pageBuffByEMConfIdx( CFSecAuthorization Authorization,
		UUID EMailConfirmUuid,
		UUID priorSecUserId )
	{
		final String S_ProcName = "pageBuffByEMConfIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtPageBuffByEMConfIdx = null;
		List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
		try {
			stmtPageBuffByEMConfIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".pg_secuserbyemconfidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtPageBuffByEMConfIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtPageBuffByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByEMConfIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByEMConfIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( EMailConfirmUuid != null ) {
				stmtPageBuffByEMConfIdx.setString( argIdx++, EMailConfirmUuid.toString() );
			}
			else {
				stmtPageBuffByEMConfIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( priorSecUserId != null ) {
				stmtPageBuffByEMConfIdx.setString( argIdx++, priorSecUserId.toString() );
			}
			else {
				stmtPageBuffByEMConfIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtPageBuffByEMConfIdx.execute();
			resultSet = (ResultSet)stmtPageBuffByEMConfIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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
			if( stmtPageBuffByEMConfIdx != null ) {
				try {
					stmtPageBuffByEMConfIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtPageBuffByEMConfIdx = null;
			}
		}
	}

	/**
	 *	Read a page array of the specific SecUser buffer instances identified by the duplicate key PwdResetIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argPasswordResetUuid	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecUserBuff[] pageBuffByPwdResetIdx( CFSecAuthorization Authorization,
		UUID PasswordResetUuid,
		UUID priorSecUserId )
	{
		final String S_ProcName = "pageBuffByPwdResetIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtPageBuffByPwdResetIdx = null;
		List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
		try {
			stmtPageBuffByPwdResetIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".pg_secuserbypwdresetidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtPageBuffByPwdResetIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtPageBuffByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByPwdResetIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByPwdResetIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( PasswordResetUuid != null ) {
				stmtPageBuffByPwdResetIdx.setString( argIdx++, PasswordResetUuid.toString() );
			}
			else {
				stmtPageBuffByPwdResetIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( priorSecUserId != null ) {
				stmtPageBuffByPwdResetIdx.setString( argIdx++, priorSecUserId.toString() );
			}
			else {
				stmtPageBuffByPwdResetIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtPageBuffByPwdResetIdx.execute();
			resultSet = (ResultSet)stmtPageBuffByPwdResetIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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
			if( stmtPageBuffByPwdResetIdx != null ) {
				try {
					stmtPageBuffByPwdResetIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtPageBuffByPwdResetIdx = null;
			}
		}
	}

	/**
	 *	Read a page array of the specific SecUser buffer instances identified by the duplicate key DefDevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argDfltDevUserId	The SecUser key attribute of the instance generating the id.
	 *
	 *	@param	argDfltDevName	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecUserBuff[] pageBuffByDefDevIdx( CFSecAuthorization Authorization,
		UUID DfltDevUserId,
		String DfltDevName,
		UUID priorSecUserId )
	{
		final String S_ProcName = "pageBuffByDefDevIdx";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtPageBuffByDefDevIdx = null;
		List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
		try {
			stmtPageBuffByDefDevIdx = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".pg_secuserbydefdevidx( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtPageBuffByDefDevIdx.registerOutParameter( argIdx++, OracleTypes.CURSOR );
			stmtPageBuffByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByDefDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByDefDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( DfltDevUserId != null ) {
				stmtPageBuffByDefDevIdx.setString( argIdx++, DfltDevUserId.toString() );
			}
			else {
				stmtPageBuffByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( DfltDevName != null ) {
				stmtPageBuffByDefDevIdx.setString( argIdx++, DfltDevName );
			}
			else {
				stmtPageBuffByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( priorSecUserId != null ) {
				stmtPageBuffByDefDevIdx.setString( argIdx++, priorSecUserId.toString() );
			}
			else {
				stmtPageBuffByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtPageBuffByDefDevIdx.execute();
			resultSet = (ResultSet)stmtPageBuffByDefDevIdx.getObject( 1 );
			if( resultSet != null ) {
				try {
					while( resultSet.next() ) {
						CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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
			if( stmtPageBuffByDefDevIdx != null ) {
				try {
					stmtPageBuffByDefDevIdx.close();
				}
				catch( SQLException e ) {
				}
				stmtPageBuffByDefDevIdx = null;
			}
		}
	}

	public void updateSecUser( CFSecAuthorization Authorization,
		CFSecSecUserBuff Buff )
	{
		final String S_ProcName = "updateSecUser";
		ResultSet resultSet = null;
		Connection cnx = schema.getCnx();
		CallableStatement stmtUpdateByPKey = null;
		List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
		try {			UUID SecUserId = Buff.getRequiredSecUserId();
			String LoginId = Buff.getRequiredLoginId();
			String EMailAddress = Buff.getRequiredEMailAddress();
			UUID EMailConfirmUuid = Buff.getOptionalEMailConfirmUuid();
			UUID DfltDevUserId = Buff.getOptionalDfltDevUserId();
			String DfltDevName = Buff.getOptionalDfltDevName();
			String PasswordHash = Buff.getRequiredPasswordHash();
			UUID PasswordResetUuid = Buff.getOptionalPasswordResetUuid();
			int Revision = Buff.getRequiredRevision();
			stmtUpdateByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".upd_secuser( ?, ?, ?, ?, ?, ?, ?" + ", "
					+	"?" + ", "
					+	"?" + ", "
					+	"?" + ", "
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
			stmtUpdateByPKey.setString( argIdx++, "a011" );
			stmtUpdateByPKey.setString( argIdx++, SecUserId.toString() );
			stmtUpdateByPKey.setString( argIdx++, LoginId );
			stmtUpdateByPKey.setString( argIdx++, EMailAddress );
			if( EMailConfirmUuid != null ) {
				stmtUpdateByPKey.setString( argIdx++, EMailConfirmUuid.toString() );
			}
			else {
				stmtUpdateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( DfltDevUserId != null ) {
				stmtUpdateByPKey.setString( argIdx++, DfltDevUserId.toString() );
			}
			else {
				stmtUpdateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( DfltDevName != null ) {
				stmtUpdateByPKey.setString( argIdx++, DfltDevName );
			}
			else {
				stmtUpdateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtUpdateByPKey.setString( argIdx++, PasswordHash );
			if( PasswordResetUuid != null ) {
				stmtUpdateByPKey.setString( argIdx++, PasswordResetUuid.toString() );
			}
			else {
				stmtUpdateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtUpdateByPKey.setInt( argIdx++, Revision );
			stmtUpdateByPKey.execute();
			resultSet = (ResultSet)stmtUpdateByPKey.getObject( 1 );
			if( resultSet != null ) {
				try {
					if( resultSet.next() ) {
						CFSecSecUserBuff updatedBuff = unpackSecUserResultSetToBuff( resultSet );
						if( resultSet.next() ) {
							resultSet.last();
							throw new CFLibRuntimeException( getClass(),
								S_ProcName,
								"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
						}
				Buff.setRequiredLoginId( updatedBuff.getRequiredLoginId() );
				Buff.setRequiredEMailAddress( updatedBuff.getRequiredEMailAddress() );
				Buff.setOptionalEMailConfirmUuid( updatedBuff.getOptionalEMailConfirmUuid() );
				Buff.setOptionalDfltDevUserId( updatedBuff.getOptionalDfltDevUserId() );
				Buff.setOptionalDfltDevName( updatedBuff.getOptionalDfltDevName() );
				Buff.setRequiredPasswordHash( updatedBuff.getRequiredPasswordHash() );
				Buff.setOptionalPasswordResetUuid( updatedBuff.getOptionalPasswordResetUuid() );
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
						"upd_secuser() did not return a valid result cursor" );
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
					"upd_secuser() did not return a result cursor" );
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

	public void deleteSecUser( CFSecAuthorization Authorization,
		CFSecSecUserBuff Buff )
	{
		final String S_ProcName = "deleteSecUser";
		Connection cnx = schema.getCnx();
		CallableStatement stmtDeleteByPKey = null;
		try {
			UUID SecUserId = Buff.getRequiredSecUserId();
			stmtDeleteByPKey = cnx.prepareCall( "begin " + schema.getLowerDbSchemaName() + ".dl_secuser( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end;" );
			int argIdx = 1;
			stmtDeleteByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByPKey.setString( argIdx++, SecUserId.toString() );
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

	public void deleteSecUserByIdIdx( CFSecAuthorization Authorization,
		UUID argSecUserId )
	{
		final String S_ProcName = "deleteSecUserByIdIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_secuserbyididx( ?, ?, ?, ?, ?" + ", "
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
				stmtDeleteByIdIdx.setString( argIdx++, argSecUserId.toString() );
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

	public void deleteSecUserByIdIdx( CFSecAuthorization Authorization,
		CFSecSecUserPKey argKey )
	{
		deleteSecUserByIdIdx( Authorization,
			argKey.getRequiredSecUserId() );
	}

	public void deleteSecUserByULoginIdx( CFSecAuthorization Authorization,
		String argLoginId )
	{
		final String S_ProcName = "deleteSecUserByULoginIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_secuserbyuloginidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " ); end";
				if( stmtDeleteByULoginIdx == null ) {
					stmtDeleteByULoginIdx = cnx.prepareStatement( sql );
				}
				int argIdx = 1;
				stmtDeleteByULoginIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByULoginIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
				stmtDeleteByULoginIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
				stmtDeleteByULoginIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByULoginIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
				stmtDeleteByULoginIdx.setString( argIdx++, argLoginId );
				int rowsUpdated = stmtDeleteByULoginIdx.executeUpdate();
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

	public void deleteSecUserByULoginIdx( CFSecAuthorization Authorization,
		CFSecSecUserByULoginIdxKey argKey )
	{
		deleteSecUserByULoginIdx( Authorization,
			argKey.getRequiredLoginId() );
	}

	public void deleteSecUserByEMConfIdx( CFSecAuthorization Authorization,
		UUID argEMailConfirmUuid )
	{
		final String S_ProcName = "deleteSecUserByEMConfIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_secuserbyemconfidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " ); end";
				if( stmtDeleteByEMConfIdx == null ) {
					stmtDeleteByEMConfIdx = cnx.prepareStatement( sql );
				}
				int argIdx = 1;
				stmtDeleteByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByEMConfIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
				stmtDeleteByEMConfIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
				stmtDeleteByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
				if( argEMailConfirmUuid != null ) {
					stmtDeleteByEMConfIdx.setString( argIdx++, argEMailConfirmUuid.toString() );
				}
				else {
					stmtDeleteByEMConfIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
				}
				int rowsUpdated = stmtDeleteByEMConfIdx.executeUpdate();
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

	public void deleteSecUserByEMConfIdx( CFSecAuthorization Authorization,
		CFSecSecUserByEMConfIdxKey argKey )
	{
		deleteSecUserByEMConfIdx( Authorization,
			argKey.getOptionalEMailConfirmUuid() );
	}

	public void deleteSecUserByPwdResetIdx( CFSecAuthorization Authorization,
		UUID argPasswordResetUuid )
	{
		final String S_ProcName = "deleteSecUserByPwdResetIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_secuserbypwdresetidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " ); end";
				if( stmtDeleteByPwdResetIdx == null ) {
					stmtDeleteByPwdResetIdx = cnx.prepareStatement( sql );
				}
				int argIdx = 1;
				stmtDeleteByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByPwdResetIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
				stmtDeleteByPwdResetIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
				stmtDeleteByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
				if( argPasswordResetUuid != null ) {
					stmtDeleteByPwdResetIdx.setString( argIdx++, argPasswordResetUuid.toString() );
				}
				else {
					stmtDeleteByPwdResetIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
				}
				int rowsUpdated = stmtDeleteByPwdResetIdx.executeUpdate();
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

	public void deleteSecUserByPwdResetIdx( CFSecAuthorization Authorization,
		CFSecSecUserByPwdResetIdxKey argKey )
	{
		deleteSecUserByPwdResetIdx( Authorization,
			argKey.getOptionalPasswordResetUuid() );
	}

	public void deleteSecUserByDefDevIdx( CFSecAuthorization Authorization,
		UUID argDfltDevUserId,
		String argDfltDevName )
	{
		final String S_ProcName = "deleteSecUserByDefDevIdx";
		ResultSet resultSet = null;
		try {
				Connection cnx = schema.getCnx();
				String sql = "begin call " + schema.getLowerDbSchemaName() + ".dl_secuserbydefdevidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " ); end";
				if( stmtDeleteByDefDevIdx == null ) {
					stmtDeleteByDefDevIdx = cnx.prepareStatement( sql );
				}
				int argIdx = 1;
				stmtDeleteByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByDefDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
				stmtDeleteByDefDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
				stmtDeleteByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
				stmtDeleteByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
				if( argDfltDevUserId != null ) {
					stmtDeleteByDefDevIdx.setString( argIdx++, argDfltDevUserId.toString() );
				}
				else {
					stmtDeleteByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
				}
				if( argDfltDevName != null ) {
					stmtDeleteByDefDevIdx.setString( argIdx++, argDfltDevName );
				}
				else {
					stmtDeleteByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
				}
				int rowsUpdated = stmtDeleteByDefDevIdx.executeUpdate();
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

	public void deleteSecUserByDefDevIdx( CFSecAuthorization Authorization,
		CFSecSecUserByDefDevIdxKey argKey )
	{
		deleteSecUserByDefDevIdx( Authorization,
			argKey.getOptionalDfltDevUserId(),
			argKey.getOptionalDfltDevName() );
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
		S_sqlSelectSecUserBuff = null;
		S_sqlSelectSecUserDistinctClassCode = null;

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
		if( stmtDeleteByULoginIdx != null ) {
			try {
				stmtDeleteByULoginIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByULoginIdx = null;
		}
		if( stmtDeleteByEMConfIdx != null ) {
			try {
				stmtDeleteByEMConfIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByEMConfIdx = null;
		}
		if( stmtDeleteByPwdResetIdx != null ) {
			try {
				stmtDeleteByPwdResetIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByPwdResetIdx = null;
		}
		if( stmtDeleteByDefDevIdx != null ) {
			try {
				stmtDeleteByDefDevIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByDefDevIdx = null;
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
		if( stmtReadBuffByULoginIdx != null ) {
			try {
				stmtReadBuffByULoginIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtReadBuffByULoginIdx = null;
		}
		if( stmtReadBuffByEMConfIdx != null ) {
			try {
				stmtReadBuffByEMConfIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtReadBuffByEMConfIdx = null;
		}
		if( stmtPageBuffByEMConfIdx != null ) {
			try {
				stmtPageBuffByEMConfIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtPageBuffByEMConfIdx = null;
		}
		if( stmtReadBuffByPwdResetIdx != null ) {
			try {
				stmtReadBuffByPwdResetIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtReadBuffByPwdResetIdx = null;
		}
		if( stmtPageBuffByPwdResetIdx != null ) {
			try {
				stmtPageBuffByPwdResetIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtPageBuffByPwdResetIdx = null;
		}
		if( stmtReadBuffByDefDevIdx != null ) {
			try {
				stmtReadBuffByDefDevIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtReadBuffByDefDevIdx = null;
		}
		if( stmtPageBuffByDefDevIdx != null ) {
			try {
				stmtPageBuffByDefDevIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtPageBuffByDefDevIdx = null;
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
		if( stmtDeleteByULoginIdx != null ) {
			try {
				stmtDeleteByULoginIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtDeleteByULoginIdx = null;
		}
		if( stmtDeleteByEMConfIdx != null ) {
			try {
				stmtDeleteByEMConfIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtDeleteByEMConfIdx = null;
		}
		if( stmtDeleteByPwdResetIdx != null ) {
			try {
				stmtDeleteByPwdResetIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtDeleteByPwdResetIdx = null;
		}
		if( stmtDeleteByDefDevIdx != null ) {
			try {
				stmtDeleteByDefDevIdx.close();
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			stmtDeleteByDefDevIdx = null;
		}
	}
}
