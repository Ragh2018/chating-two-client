import java.io.*;
import java.net.*;
class ChatClient
{
static String user;
public static void main(String s[])
{
try
{
System.out.println("client started");
Socket sk=new Socket("192.168.0.2",1500);
System.out.println("connection established");
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.println("Enter the username.");
user=br.readLine();
DataOutputStream dout=new DataOutputStream(sk.getOutputStream());
DataInputStream din=new DataInputStream(sk.getInputStream());
dout.writeUTF("login#"+user);
dout.flush();
String verify=din.readUTF();
if("correct".equals(verify))
{
System.out.println("Welcome "+user);
new ReaderThread(din);
startChat(dout);
}
else
{
System.out.println("This user already connected,reconnect with the different username");
}
}
catch(Exception e)
{
e.printStackTrace();
}
}
static void startChat(DataOutputStream dout)
{
try
{
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.println("Start to enter the message,Enter the 'Stop' to terminate");
while(true)
{
String msg=br.readLine();
if(msg.equals("stop"))
{
msg=user+":"+msg;
dout.writeUTF("logout#"+msg);
dout.flush();
break;
}
else
{
msg=user+":"+msg;
dout.writeUTF("message#"+msg);
dout.flush();
}
}
}
catch(Exception e)
{
e.printStackTrace();
}
}
}
class ReaderThread extends Thread
{
DataInputStream din;
ReaderThread(DataInputStream din)
{
this.din=din;
setDaemon(true);
start();
}
public void run()
{
try
{
while(true)
{
String msg=din.readUTF();
System.out.println(msg);
}
}
catch(Exception e)
{
e.printStackTrace();
}
}
}

