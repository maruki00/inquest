package app;


import domain.contract.IRepository;
import domain.contract.ParserService;

public class App {
    public IRepository   repository;
    public ParserService service;
    public App(IRepository repository, ParserService service){
        this.repository = repository;
        this.service = service;
    } 
}
