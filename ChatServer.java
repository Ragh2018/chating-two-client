import java.io.*;
import java.net.*;
class ChatServer
{
static int count;
static Socket sk[]=new Socket[2];
public static void main(String s[])
{
try
{
ServerSocket ss=new ServerSocket(1500);
String users[]=new String[2];
count=0;
System.out.println("Waiting for client request...");
Socket sk1=ss.accept();
sk[0]=sk1;
new ClientThread(sk1,users);
System.out.println("waiting for client request...");
Socket sk2=ss.accept();
sk[1]=sk2;
new ClientThread(sk2,users);
}
catch(Exception e)
{
e.printStackTrace();
}
}
}
class ClientThread extends Thread
{
String users[];
Socket sk;
ClientThread(Socket sk,String users[])
{
this.users=users;
this.sk=sk;
start();
}
public void run()
{
try
{
DataInputStream din=new DataInputStream(sk.getInputStream());
DataOutputStream dout=new DataOutputStream(sk.getOutputStream());
String usr=din.readUTF();
if(ChatServer.count==1)
{
if(users[0].equals(usr))
{
dout.writeUTF("incorrect");
dout.flush();
return;
}
else
{
dout.writeUTF("correct");
dout.flush();
}
}
else
{
users[0]=usr;
ChatServer.count++;
dout.writeUTF("correct");
dout.flush();
}
while(true)
{
String str=din.readUTF();
for(int i=0;i<=1;i++)
{
DataOutputStream dout2=new DataOutputStream(ChatServer.sk[i].getOutputStream());
if(ChatServer.sk[i]!=sk)
{
dout2.writeUTF(str);
dout2.flush();
}
}
}
}
catch(Exception e)
{
e.printStackTrace();
}
}
}