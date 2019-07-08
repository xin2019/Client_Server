import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "Servlet2")
public class Servlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inJson = null;// 保存HTTP客户端请求报文
        String outJson = null;// 保存HTTP服务端输出报文

        // 获得输人报文然后打印出来
        inJson = getInJson(request);

        JSONObject jsonObject = JSONObject.fromObject(inJson);
        String name = (String) jsonObject.get("name");
        String password = (String) jsonObject.get("pass");
        System.out.println(name + "," + password);
        //逻辑处理登陆验证部分，待开发

        // 下面部分是输出部分的处理,待填充
        outJson = inJson;

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(outJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get");
    }

    // 获得请求的报文,并作简单的校验
    protected String getInJson(HttpServletRequest request) throws IOException {

        byte buffer[] = new byte[64 * 1024];
        InputStream in = request.getInputStream();// 获取输入流对象

        int len = in.read(buffer);
        // 必须对数组长度进行判断，否则在new byte[len]会报NegativeArraySizeException异常
        if (len < 0) {
            throw new IOException("请求报文为空");
        }

        String encode = request.getCharacterEncoding();// 获取请求头编码
        // 必须对编码进行校验,否则在new String(data, encode);会报空指针异常
        if (null == encode || encode.trim().length() < 0) {
            throw new IOException("请求报文未指明请求编码");
        }

        byte data[] = new byte[len];

        // 把buffer数组的值复制到data数组
        System.arraycopy(buffer, 0, data, 0, len);

        // 通过使用指定的 charset 解码指定的 byte 数组，构造一个新的 String
        String inJson = new String(data, encode);

        return inJson;
    }
}
