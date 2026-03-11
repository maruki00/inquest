package Interface;


import java.util.List;

import app.Services.HttpdAccessLogServcie;
import infra.repository.InMemory;
import infra.templates.DDoS;
import infra.templates.Injection;

public class Main {
        public static void main(String[] args) {
                var rep = new InMemory();
                var srv = new HttpdAccessLogServcie(rep);
                srv.Parse("/home/user/dev/lab/src/main/java/Interface/access.log");
                var templates =  List.of(
                        new DDoS(),
                        new Injection()
                );
                for (var obj : templates){
                        srv.Analyse(obj);
                }
             //  srv.Analyse(new Injection());
        }
}

