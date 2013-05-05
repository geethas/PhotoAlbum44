package com.example.photoalbumapp; 

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import android.os.Environment;

/** @author Adithya Pothuri, Geetha Srinivasan */

public class Backend {

	public User readUser(String userID) throws ClassNotFoundException, IOException {
		/* Deserialize an object */

		User user=null; 
		String pathname = "data/users/" + userID + ".ser";
		String location = "data/users/";

		File dir = new File(location);

		for(File file : dir.listFiles())
		{
			if (file.getName().startsWith(userID) && file.getName().endsWith((".ser"))) {
				FileInputStream fin = new FileInputStream(pathname);
				ObjectInputStream fileIn = new ObjectInputStream(fin);
				user = (User) fileIn.readObject();
				fileIn.close();
				fin.close();
				return user;
			}
		}
		return null;
	}


	public User addUser(User u) throws IOException {

		/* Serialize an object during logout so get user from userlist*/
		//System.out.println("user name is  : " + u.getId());

		//String pathname = "users/" + u.getId() + ".ser";
		File file = new File("users/myuser.ser");
		FileOutputStream fw = new FileOutputStream(file);
		

		ObjectOutputStream fileOut = new ObjectOutputStream(fw);
		fileOut.writeObject(u);
		fileOut.close();
		fw.close();


		return u;
	}


	public void writeUser(User u) throws FileNotFoundException, IOException {
		/* Serialize an object during logout so get user from userlist*/
		String pathname = "data/users/" + u.getId() + ".ser";
		String location = "data/users/";

		File dir = new File(location);
		//if theres no files in data
		if(dir.listFiles().length == 0){
			FileOutputStream fw = new FileOutputStream(pathname);
			ObjectOutputStream fileOut = new ObjectOutputStream(fw);
			fileOut.writeObject(u);
			fileOut.close();
			fw.close();
		}
		for (File file : dir.listFiles()) {
			//file already exists so overwrite it
			if (file.getName().startsWith(u.getId()) && file.getName().endsWith((".ser"))) {
				FileOutputStream fw = new FileOutputStream(pathname);
				ObjectOutputStream fileOut = new ObjectOutputStream(fw);
				fileOut.writeObject(u);
				fileOut.close();
				fw.close();
			}
			else{ //creating file
				FileOutputStream fw = new FileOutputStream(pathname);
				ObjectOutputStream fileOut = new ObjectOutputStream(fw);
				fileOut.writeObject(u);
				fileOut.close();
				fw.close();
			}
		}
	}

	public boolean deleteUser(String userID) throws IOException, ClassNotFoundException {
		String location = "data/users/";
		File dir = new File(location);

		for (File file : dir.listFiles()) {
			//file exists so delete it
			if (file.getName().startsWith(userID) && file.getName().endsWith((".ser"))) {
				file.delete();
				return true;
			}
		}
		return false;

	}

	public ArrayList<String> getUserList () {

		String location = "data/users/";
		File dir = new File(location);
		ArrayList <String> users = new ArrayList<String>();

		for (File file : dir.listFiles()) {

			//Retrieve all files
			users.add(file.getName().substring(0, file.getName().indexOf(".")));
		}
		return users;
	}


	public Album findAlbum(String albumName, User user) {
		for(Album a : user.getUserAlbums()){
			if(a.getName().equals(albumName)){
				return a;
			}
		}
		return null;
	}

	public Photo findPhoto(String filename, Album album) {
		if(album == null){
			return null;
		}
		for(Photo p : album.getPhotos()){
			if(p.getFileName().equals(filename)){
				return p;
			}
		}
		return null;
	}
}