

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import net.sf.json.JSONObject;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class LoginClient extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    LoginClient frame = new LoginClient();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public LoginClient() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblAccount = new JLabel("Account");
        lblAccount.setBounds(61, 48, 54, 15);
        contentPane.add(lblAccount);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(61, 119, 54, 15);
        contentPane.add(lblPassword);

        textField = new JTextField();
        textField.setBounds(149, 45, 222, 21);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(149, 116, 222, 21);
        contentPane.add(passwordField);

        JButton btnLoginClient = new JButton("Login");
        btnLoginClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name=textField.getText();
                char[] tempPass=passwordField.getPassword();
                String password=new String(tempPass);
                JSONObject obj=new JSONObject();
                obj.put("name", name);
                obj.put("pass", password);
                HttpClient httpClient=new HttpClient();
                byte[] jsonBytes=obj.toString().getBytes();
                RequestEntity requestEntity=new ByteArrayRequestEntity(jsonBytes);
                PostMethod postMethod=new PostMethod();
                postMethod.setRequestEntity(requestEntity);
                postMethod.setPath("http://localhost:8080/Servlet2");
                postMethod.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
                httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5*1000);
                httpClient.getHttpConnectionManager().getParams().setSoTimeout(20*1000);
                String responseMsg="";
                int statusCode=0;
                try {
                    statusCode=httpClient.executeMethod(postMethod);
                    responseMsg=postMethod.getResponseBodyAsString();
                } catch (HttpException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }finally{
                    postMethod.releaseConnection();
                }
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("HTTP·þÎñÒì³£:" + statusCode);
                }
                System.out.println(statusCode+responseMsg);

            }

        });
        btnLoginClient.setBounds(143, 195, 93, 23);
        contentPane.add(btnLoginClient);
    }
}
