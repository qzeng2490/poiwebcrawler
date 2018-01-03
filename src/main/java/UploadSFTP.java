import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileInputStream;

public class UploadSFTP {
    public static boolean upLoadData(){

        String SFTPHOST = "10.10.6.57";
        int SFTPPORT = 22;
        String SFTPUSER = "zengqiang";
        String SFTPPASS = "88888888";
        String SFTPWORKINGDIR = "/home/zengqiang";

        String DB_NAME = "/Users/zengqiang/Downloads/shunde2/fspoi.csv";

        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        System.out.println("preparing the host information for sftp.");
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
            session.setPassword(SFTPPASS);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            System.out.println("Host connected.");
            channel = session.openChannel("sftp");
            channel.connect();
            System.out.println("sftp channel opened and connected.");
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(SFTPWORKINGDIR);
            File f = new File(DB_NAME);
            channelSftp.put(new FileInputStream(f), "测试.scv");
            return true;
        } catch (Exception ex) {
            System.out.println("Exception found while tranfer the response.");
        }
        finally{

            channelSftp.exit();
            System.out.println("sftp Channel exited.");
            channel.disconnect();
            System.out.println("Channel disconnected.");
            session.disconnect();
            System.out.println("Host Session disconnected.");
        }
        return false;

    }

    public static void main(String[] args) {
        upLoadData();
    }
}
