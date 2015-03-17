package marek.maturita;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;


public class Client extends ActionBarActivity {
    private ByteBuffer buffer;
    int port = 2345;
    DatagramSocket udpSocket;
    private DatagramChannel udpChannel;
    int hex = 0x00;
    DatagramSocket sock = null;
    String s;
    int i;
    int hex_1;
    int hex_2;
    int hex_3;
    int hex_4;
    int slider = 0;
    TextView hostname = (TextView)findViewById(R.id.hostname);
    TextView status = (TextView)findViewById(R.id.status);
    TextView status1 = (TextView)findViewById(R.id.status1);
    TextView display = (TextView)findViewById(R.id.display);
    private void client ()
    {
        try {
            //Využímvám nejdelší možné délky packetu.
            byte[] packetData = new byte[512];
            DatagramPacket packet = new DatagramPacket(packetData, 512); //vytvoří packet
            String hostname_string;
            //Zjistí hodnotu řetězce zadného pro název
            hostname_string = (String) hostname.getText();
            sock = new DatagramSocket();
            InetAddress host = InetAddress.getByName(hostname_string);
            //Vyrtesetování hodnot poslaných
            udpSocket = new DatagramSocket();
            udpSocket.setSoTimeout( 1000 );
            packet.setSocketAddress(new InetSocketAddress(hostname_string, port));
            udpSocket.send(packet);
            System.out.println("Packet odeslan");


            String messages = (String) display.getText();
            String messages2 = messages.toUpperCase();
            hex = hex + 0x1;

            //Naše abeceda
            int disp_char[]=
                    {
                            0x77,0x7c,0x39,0x5e,0x79,0x71,
                            0x3d,0x74,0x30,0x1e,0x00,0x38,
                            0x37,0x54,0x5c,0x73,0x00,0x50,
                            0x6d,0x78,0x3e,0x1c,0x00,0x00,
                            0x6e,0x5b
                    };

            int disp_num []={0x3f,0x06,0x5b,0x4f,0x66,0x6d,0x7d,0x07,0x7f,0x6f};

                /*
                TODO: Abstrakce přeměny znaku na číslo
                První pokus přes switch
                Done
                */
            int hexp[]= {0,0,0,0};
            for (i=0;i<4;i++) {
                char ch = messages2.charAt(i);

                switch (ch) {

                    case 'A':
                        hexp[i] = disp_char[0];

                        break;
                    case 'B':
                        hexp[i] = disp_char[1];
                        break;
                    case 'C':
                        hexp[i] = disp_char[2];
                        break;
                    case 'D':
                        hexp[i] = disp_char[3];
                        break;
                    case 'E':
                        hexp[i] = disp_char[4];
                        break;
                    case 'F':
                        hexp[i] = disp_char[5];
                        break;
                    case 'G':
                        hexp[i] = disp_char[6];
                        break;
                    case 'H':
                        hexp[i] = disp_char[7];
                        break;
                    case 'I':
                        hexp[i] = disp_char[8];
                        break;
                    case 'J':
                        hexp[i] = disp_char[9];
                        break;
                    case 'L':
                        hexp[i] = disp_char[11];
                        break;
                    case 'M':
                        hexp[i] = disp_char[12];
                        break;
                    case 'N':
                        hexp[i] = disp_char[13];
                        break;
                    case 'O':
                        hexp[i] = disp_char[14];
                        break;
                    case 'P':
                        hexp[i] = disp_char[15];
                        break;
                    case 'R':
                        hexp[i] = disp_char[17];
                        break;
                    case 'S':
                        hexp[i] = disp_char[18];
                        break;
                    case 'T':
                        hexp[i] = disp_char[19];
                        break;
                    case 'U':
                        hexp[i] = disp_char[20];
                        break;
                    case 'V':
                        hexp[i] = disp_char[21];
                        break;
                    case 'Y':
                        hexp[i] = disp_char[22];
                        break;
                    case 'Z':
                        hexp[i] = disp_char[23];
                        break;
                         /*
                            Teď přijdou na řadu čísla
                            Now will come numbers
                         */
                    case '1':
                        hexp[i] = disp_num[1];

                        break;
                    case '2':
                        hexp[i] = disp_num[2];
                        break;
                    case '3':
                        hexp[i] = disp_num[3];
                        break;
                    case '4':
                        hexp[i] = disp_num[4];
                        break;
                    case '5':
                        hexp[i] = disp_num[5];
                        break;
                    case '6':
                        hexp[i] = disp_num[6];
                        break;
                    case '7':
                        hexp[i] = disp_num[7];
                        break;
                    case '8':
                        hexp[i] = disp_num[8];
                        break;
                    case '9':
                        hexp[i] = disp_num[9];
                        break;
                    case '0':
                        hexp[i] = disp_num[0];
                        break;
                    default:
                        hexp[i] = disp_num[10];
                        break;
                }
            }
            hex_1 = hexp[0];
            hex_2 = hexp[1];
            hex_3 = hexp[2];
            hex_4 = hexp[3];
            byte[] bytes = new byte[] { (byte) slider, (byte) hex_1, (byte) hex_2, (byte) hex_3, (byte) hex_4};
            DatagramPacket  dp = new DatagramPacket(bytes , bytes.length , host , port);
            sock.send(dp);

            System.out.println(messages2);

            System.out.println(hex_1);System.out.println(hex_2);System.out.println(hex_3);System.out.println(hex_4);

                /*
                //buffer to receive incoming data
                byte[] buffer = new byte[65536];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                sock.receive(reply);

                byte[] data = reply.getData();
            String str = new String(data, 0, reply.getLength());

                //Printing the details of incoming data - client ip : client port - client message
                System.out.println(reply.getAddress().getHostAddress() + " : " + reply.getPort() + " - " + str.getBytes());
               */


        }
        catch (IOException e) {
            e.printStackTrace(System.err);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
