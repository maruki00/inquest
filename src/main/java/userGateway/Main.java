package userGateway;

import app.App;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
        public static void main(String[] args) {
                Dotenv env = Dotenv.load();
                var app = new App(env);
                try{
                        app.Run();
                }catch(Exception e){
                        System.err.println("Error Running App : "+ e.getMessage());
                }
        }
}