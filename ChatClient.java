import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.*;
import java.io.*;


public class ChatClient extends JFrame{
    JTextArea messages;
    JTextField textbox;
    JButton send;
    JLabel title;
    JScrollPane scroll;
    DataOutputStream dos;
    DataInputStream dis;
    public static void main(String s[]) throws Exception{

        ChatClient ch = new ChatClient();
    }
    public ChatClient() throws Exception{
        final Socket client=new Socket("localhost",1111);
        OutputStream os=client.getOutputStream();
        dos=new DataOutputStream(os);
        InputStream is=client.getInputStream();
        dis=new DataInputStream(is);

        setSize(525, 600);
        setLayout(null);
        setResizable(false);
        title = new JLabel("kChat");
        send = new JButton(">>>");
        textbox = new JTextField("");
        messages = new JTextArea("");
        scroll = new JScrollPane (messages, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setTitle("kChat Application Client");
        getContentPane().setBackground(Color.PINK);
        title.setBounds(0, 0, 500, 50);
        messages.setBounds(50, 50, 400, 400);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        textbox.setBounds(50, 500, 300, 30);
        messages.setEditable(false);
        send.setBounds(375, 500, 75, 30);
        add(title);
        add(messages);
        add(textbox);
        add(send);
        getRootPane().setDefaultButton(send);
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateFormat dateFormat =new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                if(!textbox.getText().matches("[\\s]*"))
                    messages.append("Me[ @"+ dateFormat.format(date) +" ]: "+ textbox.getText() + "\n");
               
                   try{

                    dos.writeUTF("Him[ @"+ dateFormat.format(date) +" ]: "+ textbox.getText() + "\n");
                      }
                      catch(Exception esss)
                      {

                      }
                if(textbox.getText().equals("quit()")){
                    try {
                        dis.close();
                        dos.close();
                        client.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                textbox.setText("");
            }
        });
        getContentPane().add(scroll);
        setVisible(true);
        while(true)
        {
            String msg = dis.readUTF();
            if (msg.matches("quit()")){
                try {
                    dis.close();
                    dos.close();
                    client.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            messages.append(msg);
        }
       // client.close();

    }
}
