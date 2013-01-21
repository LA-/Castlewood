package com.castlewood;

import io.netty.util.AttributeKey;

import java.math.BigInteger;

import com.castlewood.world.model.entity.mob.player.Client;

public class Constants
{

	public static final boolean ENABLE_RSA = true;

	public static final byte ARCHIVE_META_SIZE = 6;

	public static final byte BLOCK_HEADER = 8;

	public static final byte SERVICE_LOGIN = 14, SERVICE_ONDEMAND = 15;

	public static final byte STATUS_EXCHANGE_DATA = 0, STATUS_DELAY = 1,
			STATUS_OK = 2, STATUS_INVALID_CREDENTIALS = 3,
			STATUS_ACCOUNT_DISABLED = 4, STATUS_ACCOUNT_ONLINE = 5,
			STATUS_GAME_UPDATED = 6, STATUS_SERVER_FULL = 7,
			STATUS_LOGIN_SERVER_OFFLINE = 8, STATUS_TOO_MANY_CONNECTIONS = 9,
			STATUS_BAD_SESSION_ID = 10,
			STATUS_LOGIN_SERVER_REJECTED_SESSION = 11,
			STATUS_MEMBERS_ACCOUNT_REQUIRED = 12,
			STATUS_COULD_NOT_COMPLETE = 13, STATUS_UPDATING = 14,
			STATUS_RECONNECTION_OK = 15, STATUS_TOO_MANY_LOGINS = 16,
			STATUS_IN_MEMBERS_AREA = 17, STATUS_INVALID_LOGIN_SERVER = 20,
			STATUS_PROFILE_TRANSFER = 21;

	public static final byte LOGIN_TYPE_CONNECTING = 16,
			LOGIN_TYPE_RECONNECTING = 18;

	public static final short CLIENT_REVISION = 317;

	public static final short BLOCK_SIZE = 512, BLOCK_DATA = BLOCK_SIZE
			+ BLOCK_HEADER, ONDEMAND_BLOCK_SIZE = 500;

	public static final short MAXIMUM_PLAYERS = 2000;

	public static final int PORT_JAGGRAB = 43595, PORT_GAME = 43594;

	public static final int MASK_PLAYER_FACE = 0x1, MASK_PLAYER_FOCUS = 0x2,
			MASK_PLAYER_FORCED_CHAT = 0x4, MASK_PLAYER_ANIMATION = 0x8,
			MASK_PLAYER_APPEARANCE = 0x10, MASK_PLAYER_FIRST_HIT = 0x20,
			MASK_PLAYER_CHAT = 0x80, MASK_PLAYER_GRAPHICS = 0x100,
			MASK_PLAYER_SECOND_HIT = 0x200, MASK_PLAYER_FORCE_MOVEMENT = 0x400;

	public static final String CACHE_PATH = ".castlewood_file_store_32";

	public static final String[] JAGGRAB_ROOT =
	{
			"crc", "title", "config", "interface", "media", "versionlist",
			"textures", "wordenc", "sounds"
	};

	public static final AttributeKey<Integer> KEY_LENGTH = new AttributeKey<>(
			"LoginRequestDecoder.length");

	public static final AttributeKey<Client> KEY_CLIENT = new AttributeKey<>(
			"Client");

	public static final byte[] PACKET_LENGTHS =
	{
			0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 6, 2, 2, 0, 0, 2,
			0, 6, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 4, 0, 0, 2, 2, 6, 0, 6,
			0, -1, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 8, 0, 0, 8, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 6, 0, 2, 2, 8, 6, 0, -1, 0, 6, 0, 0, 0, 0, 0, 1, 4, 6,
			0, 0, 0, 0, 0, 0, 0, 3, 0, 0, -1, 0, 0, 13, 0, -1, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 1, 0, 6, 0, 0, 0, -1, 0, 2, 6, 0,
			4, 6, 8, 0, 6, 0, 0, 0, 2, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 1,
			2, 0, 2, 6, 0, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 8, 0, 3, 0, 2, 0, 0, 8, 1, 0, 0, 12, 0, 0, 0, 0,
			0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 4, 0, 4, 0, 0, 0, 7, 8, 0, 0, 10,
			0, 0, 0, 0, 0, 0, 0, -1, 0, 6, 0, 1, 0, 0, 0, 6, 0, 6, 8, 1, 0, 0,
			4, 0, 0, 0, 0, -1, 0, -1, 4, 0, 0, 6, 6, 0, 0,
	};

	public static final int[] BIT_MASK = new int[32];

	public static final BigInteger[] RSA_KEYS =
	{
			new BigInteger(
					"16796384801495986990901552458664862340759118417172271300430634063349155247800339137925656247174486186379285475699888732472834367117179254789052333166619489820612139573964999573492510230901542223452250878063899635289605376051869145470766340023421865902492692240731159554081462972690633548662125654807226259447"),
			new BigInteger(
					"74403104750745524048107273411815582108975629937045403823553700976827936357434685825625110818797221773100620784761128018187376799272963117053223874316698284768510783206476831061429001368871032373605708194906076390657529560682734543041240427818749810974927494339660572356650416015328096956094754719124565923289")
	};

	static
	{
		for (int i = 0; i < BIT_MASK.length; i++)
		{
			BIT_MASK[i] = (1 << i) - 1;
		}
	}

}
