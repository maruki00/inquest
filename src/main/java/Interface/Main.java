package Interface;


import app.Services.HttpdAccessLogServcie;
import infra.repository.InMemory;

public class Main {
        public static void main(String[] args) {
                var rep = new InMemory();
                var srv = new HttpdAccessLogServcie(rep);
                //InputStream is = Main.class.getClassLoader().getResourceAsStream("access.log");
                srv.Parse("/home/user/dev/lab/src/main/java/Interface/access.log");
                for(var x : rep.events()){
                        System.out.printf("event: %s\n", x.srcIp());
                }
        }
}

