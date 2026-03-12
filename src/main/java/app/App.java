package app;

import java.util.List;

import app.Services.HttpdAccessLogServcie;
import domain.contract.IRepository;
import infra.repository.InMemory;
import infra.templates.Basic;
import infra.templates.BruteForce;
import infra.templates.DDoS;
import infra.templates.Injection;
import io.github.cdimascio.dotenv.Dotenv;

public class App {
        public IRepository repository;
        public HttpdAccessLogServcie service;
        public Dotenv env;
        public App(Dotenv env) {
                this.env = env;
                this.repository = new InMemory();
                this.service = new HttpdAccessLogServcie(this.repository);
        }
        public void Run() throws Exception {
                //TODO:
                //bad implementation: should be dynamic 
                //implement args 
                this.service.Parse(ClassLoader.getSystemResource("access.log").getPath());
                var templates = List.of(
                                new Basic(),
                                new BruteForce(),
                                new Injection(),
                                new DDoS());
                for (var obj : templates) {
                        this.service.Analyse(obj, this.env);
                }
        }
}
