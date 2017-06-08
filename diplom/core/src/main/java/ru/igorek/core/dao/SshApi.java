package ru.igorek.core.dao;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import ru.igorek.core.Main;

/**
 *
 * @author Игорек
 */
public class SshApi {
    private static Logger log = Logger.getLogger(Main.class);
    
    private static final int CONNECTION_TIMEOUT = 10000;
    
    public List<String> startApplication(String resourceUrl, int port, String userLogin, String password, String command){
        List<String> lines = new ArrayList<>();
        try {
            //String command = "java -jar d:\\bkparse\\target\\bkparse-1.0-SNAPSHOT.jar";
            Session session = initSession(resourceUrl, port, userLogin, password);
            Channel channel = initChannel(command, session);
            channel.connect();
            String dataFromChannel = getDataFromChannel(channel);
            lines.addAll(Arrays.asList(dataFromChannel.split("\n")));
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            log.error(e);
        }
        return lines;
    }
    
    public Session initSession(String host, int port, String userLogin, String password) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(userLogin, host, port);
        session.setPassword(password);
        UserInfo userInfo = new ResourceUserInfo();
        session.setUserInfo(userInfo);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(CONNECTION_TIMEOUT);
        return session;
    }
    
    private Channel initChannel(String commands, Session session) throws JSchException {
        Channel channel = session.openChannel("exec");
        ChannelExec channelExec = (ChannelExec)channel;
        channelExec.setCommand(commands);
        channelExec.setErrStream(System.err);
        return channel;
    }
    
    private String getDataFromChannel(Channel channel) throws IOException {
        InputStream in = channel.getInputStream();
        StringBuilder result = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);	
	String line;
        while (true){
            while ((line = br.readLine()) != null) {
                result.append(line);
                result.append("\n");
            }
            if (channel.isClosed()) {
                int exitStatus = channel.getExitStatus();
                log.info("exit-status: " + exitStatus);
                break;
            }
        }
        return result.toString();
    }
    
    public String getDataFromLocalHost() throws IOException{
        Runtime runtime = Runtime.getRuntime();
	String cmd = "java -jar d:\\bkparse\\target\\bkparse-1.0-SNAPSHOT.jar";
	Process proc = runtime.exec(cmd);
		
	InputStream is = proc.getInputStream();
	InputStreamReader isr = new InputStreamReader(is);
	BufferedReader br = new BufferedReader(isr);
                
        StringBuilder result = new StringBuilder();
                
		
	String line;
	while ((line = br.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        return result.toString();
    }
    
    public boolean isResourceAvailable(String resourceUrl, int port){
        boolean isAvailabe = false;
        try{
            JSch jSch = new JSch();
            Session session = jSch.getSession("`",resourceUrl, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(CONNECTION_TIMEOUT);
        }
        catch(Exception e){
            if (e.getMessage().equals("Auth fail"))
                isAvailabe = true;
        }
        return isAvailabe;
    }
    
    public void closeSession(Session session){
        session.disconnect();
    }
    
    class ResourceUserInfo implements UserInfo {
        private String password;

        @Override
        public void showMessage(String message) {
            System.out.println(message);
        }

        @Override
        public boolean promptYesNo(String message) {
            System.out.println(message);
            return true;
        }

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public String getPassword() {
            return this.password;
        }

        @Override
        public boolean promptPassphrase(String arg0) {
            System.out.println(arg0);
            return true;
        }

        @Override
        public boolean promptPassword(String arg0) {
            System.out.println(arg0);
            this.password = arg0;
            return true;
        }
    }
}
