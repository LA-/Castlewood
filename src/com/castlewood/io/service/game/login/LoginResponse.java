package com.castlewood.io.service.game.login;

public class LoginResponse
{

	private int status;

	private int rights;

	private boolean flagged;

	public LoginResponse(int status, int rights, boolean flagged)
	{
		this.status = status;
		this.rights = rights;
		this.flagged = flagged;
	}

	public int getStatus()
	{
		return status;
	}

	public int getRights()
	{
		return rights;
	}

	public boolean isFlagged()
	{
		return flagged;
	}
	
}
