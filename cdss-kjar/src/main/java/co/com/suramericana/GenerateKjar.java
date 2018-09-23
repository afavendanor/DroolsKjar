package co.com.suramericana;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.io.FileUtils;
import org.apache.maven.cli.MavenCli;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import co.com.suramericana.domain.RepositoryProperties;
import co.com.suramericana.exception.TechnicalException;

public class GenerateKjar {
	
	private static void cloneRepository(RepositoryProperties repositoryProperties) {
        try {
            FileUtils.cleanDirectory(repositoryProperties.getLocalRepository());
            final CloneCommand cloneCommand = Git.cloneRepository();
            cloneCommand.setURI(repositoryProperties.getRemoteUrl())
            		.setDirectory(repositoryProperties.getLocalRepository())
                    .setCredentialsProvider(repositoryProperties.getCredentials()).call();
        } catch (IOException | GitAPIException e) {
        	System.err.println("Error en repo: " + e.getMessage());
            throw new TechnicalException(e.getMessage(), e);
        }
    }
	

	
	public static final void generateKjar() throws FileNotFoundException {
		
		try {
			
			RepositoryProperties repositoryProperties = new RepositoryProperties("https://github.com/afavendanor/rules.git"
					, "src/main/resources/rules", "afavendanor" , "xE9PWLdey/yB8XdMrkK5EA&&uxFXsPWiKaYVde30nrSg61RKN9I/IuugkIz0KlkIYSE");
						
			cloneRepository(repositoryProperties);
			
						
	        MavenCli cli = new MavenCli();
	        cli.doMain(new String[]{"clean", "install"}, ".", new PrintStream("log"), new PrintStream("log"));
	        
		} catch (Exception e) {
			System.err.println("error: " + e.getMessage());
		}
		
		
	}
	
	
	
	
	

	public static void main(String[] args) {
		
		try {
						
			generateKjar();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		

	}
	
	

}
