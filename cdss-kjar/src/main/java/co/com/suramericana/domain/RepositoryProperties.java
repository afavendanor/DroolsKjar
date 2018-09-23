package co.com.suramericana.domain;

import java.io.File;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import co.com.suramericana.security.Encryptor;

public class RepositoryProperties {

	private String remoteUrl;
	private String localUri;
	private String userName;
	private String password;
	private UsernamePasswordCredentialsProvider credentials;
	private File localRepository;

	public RepositoryProperties(String remoteUrl, String localUri, String userName, String password) {
		this.remoteUrl = remoteUrl;
		this.localUri = localUri;
		this.userName = userName;
		this.password = password;
		this.credentials = new UsernamePasswordCredentialsProvider(userName, Encryptor.decode(password));
		this.localRepository = new File(localUri);
	}

	public String getRemoteUrl() {
		return remoteUrl;
	}

	public String getLocalUri() {
		return localUri;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return Encryptor.decode(password);
	}

	public UsernamePasswordCredentialsProvider getCredentials() {
		return credentials;
	}

	public File getLocalRepository() {
		return localRepository;
	}

}
