package Interface;


import java.util.List;

import app.Services.HttpdAccessLogServcie;
import infra.repository.InMemory;
import infra.templates.Basic;
import infra.templates.BruteForce;
import infra.templates.DDoS;
import infra.templates.Injection;
import io.github.cdimascio.dotenv.Dotenv;
public class Main {
        public static void main(String[] args) {
                Dotenv dotenv = Dotenv.load();
                var rep = new InMemory();
                var srv = new HttpdAccessLogServcie(rep);
                srv.Parse(ClassLoader.getSystemResource("access.log").getPath());
                var templates =  List.of(
                        new Basic(),
                        new BruteForce(),
                        new Injection(),
                        new DDoS()
                );
                for (var obj : templates){
                        srv.Analyse(obj);
                }
        }
}

