package com.example.demo;

import static util.Helpers.printJson;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Controller
public class ControladorPrincipal {

	
	@GetMapping("/")
	 public String greeting(Model model,HttpServletRequest request) {
		//db.InsertarArticulo();		
		
		 MongoClient client = new MongoClient();
	     MongoDatabase database = client.getDatabase("pokemonDB"); //Nombre de la base de datos
	     MongoCollection<Document> collection = database.getCollection("pokemonC"); //Nombre de la coleccion

	      	/*
	        System.out.println("Find one:");
	        Document first = collection.find().first();
	        printJson(first);
	        */
	        
	        
	        
	        
	        System.out.println("Find all with into: ");
	        List<Document> all = collection.find().into(new ArrayList<Document>());
	        for (Document cur : all) {
	            //printJson(cur);
	        }
	        
	        model.addAttribute("pokemons", all);
	        
	        /*
	        System.out.println("Find all with iteration: ");
	        MongoCursor<Document> cursor = collection.find().iterator();
	        try {
	            while (cursor.hasNext()) {
	                Document cur = cursor.next();
	                printJson(cur);
	            }
	        } finally {
	            cursor.close();
	        }

	        System.out.println("Count:");
	        long count = collection.count();
	        System.out.println(count);
	        */
		
		return "prueba";
	 }
}
