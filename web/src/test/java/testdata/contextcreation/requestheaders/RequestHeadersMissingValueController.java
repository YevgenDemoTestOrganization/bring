package testdata.contextcreation.requestheaders;

import com.bobocode.bring.web.servlet.BringServlet;
import com.bobocode.bring.web.servlet.annotation.GetMapping;
import com.bobocode.bring.web.servlet.annotation.RequestHeader;
import com.bobocode.bring.web.servlet.annotation.RestController;

@RestController
public class RequestHeadersMissingValueController implements BringServlet {
    @GetMapping(path = "/header")
    public String getHeader(@RequestHeader String header) {
        return header;
    }
}
