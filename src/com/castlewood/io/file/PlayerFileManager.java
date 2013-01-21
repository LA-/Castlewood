package com.castlewood.io.file;

public interface PlayerFileManager<F extends PlayerFile>
{

	public boolean checkCredentials(String username, String password);

	public boolean save(F file);

	public F load(String username);

}
