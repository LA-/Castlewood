package com.castlewood.io.service.game.login;

import net.burtleburtle.bob.rand.IsaacRandom;

public class LoginRequest
{

	private String username;

	private String password;

	private IsaacRandom decoder;

	private IsaacRandom encoder;

	public LoginRequest(String username, String password, IsaacRandom decoder,
			IsaacRandom encoder)
	{
		this.username = username;
		this.password = password;
		this.decoder = decoder;
		this.encoder = encoder;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public IsaacRandom getDecoder()
	{
		return decoder;
	}

	public IsaacRandom getEncoder()
	{
		return encoder;
	}

}
