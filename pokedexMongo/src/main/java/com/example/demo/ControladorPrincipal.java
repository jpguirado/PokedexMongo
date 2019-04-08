package com.example.demo;

import static com.mongodb.client.model.Filters.eq;
import static util.Helpers.printJson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Controller
public class ControladorPrincipal {

	
	
	// Datos globales de acceso a BBDD
	List<Pokemon> listaPokemonCompleta;
	MongoClient client;
	MongoDatabase database;
	MongoCollection<Document> collection;
	
	/*
	 * Se invocara este metodo solo al arrancar la aplicacion, esto es necesario ya que solo queremos cargar en memoria
	 * los datos de la base de datos 1 vez
	 
	@PostConstruct
	private void initDatabase() {
		
			
	}
	*/
	
	@GetMapping("/")
	public String controladorInicio(Model model, HttpServletRequest request) {
		
		listaPokemonCompleta = new ArrayList<Pokemon>();
		
		client = new MongoClient();
		database = client.getDatabase("pokemonDB"); // Nombre de la base de datos
		collection = database.getCollection("pokemonC"); // Nombre de la coleccion

		/*
		 * Recuperamos los datos de la base de datos mongo y los introducimos en una
		 * lista de objetos de la clase Pokemon para trabajar con ellos en posteriores
		 * filtros
		 */
		List<Document> all = collection.find().into(new ArrayList<Document>());

		for (Document cur : all) {

			Pokemon pokemonAIntroducir = new Pokemon();

			pokemonAIntroducir.setHabilidades(cur.get("abilities").toString());
			pokemonAIntroducir.setAgainst_bug(cur.get("against_bug").toString());
			pokemonAIntroducir.setAgainst_dark(cur.get("against_dark").toString());
			pokemonAIntroducir.setAgainst_dragon(cur.get("against_dragon").toString());
			pokemonAIntroducir.setAgainst_electric(cur.get("against_electric").toString());
			pokemonAIntroducir.setAgainst_fairy(cur.get("against_fairy").toString());
			pokemonAIntroducir.setAgainst_fight(cur.get("against_fight").toString());
			pokemonAIntroducir.setAgainst_fire(cur.get("against_fire").toString());
			pokemonAIntroducir.setAgainst_flying(cur.get("against_flying").toString());
			pokemonAIntroducir.setAgainst_ghost(cur.get("against_ghost").toString());
			pokemonAIntroducir.setAgainst_grass(cur.get("against_grass").toString());
			pokemonAIntroducir.setAgainst_ground(cur.get("against_ground").toString());
			pokemonAIntroducir.setAgainst_ice(cur.get("against_ice").toString());
			pokemonAIntroducir.setAgainst_normal(cur.get("against_normal").toString());
			pokemonAIntroducir.setAgainst_poison(cur.get("against_poison").toString());
			pokemonAIntroducir.setAgainst_psychic(cur.get("against_psychic").toString());
			pokemonAIntroducir.setAgainst_rock(cur.get("against_rock").toString());
			pokemonAIntroducir.setAgainst_steel(cur.get("against_steel").toString());
			pokemonAIntroducir.setAgainst_water(cur.get("against_water").toString());
			pokemonAIntroducir.setAttack(cur.get("attack").toString());
			pokemonAIntroducir.setBase_egg_steps(cur.get("base_egg_steps").toString());
			pokemonAIntroducir.setBase_happiness(cur.get("base_happiness").toString());
			pokemonAIntroducir.setBase_total(cur.get("base_total").toString());
			pokemonAIntroducir.setCapture_rate(cur.get("capture_rate").toString());
			pokemonAIntroducir.setClassfication(cur.get("classfication").toString());
			pokemonAIntroducir.setDefense(cur.get("defense").toString());
			pokemonAIntroducir.setExperience_growth(cur.get("experience_growth").toString());
			pokemonAIntroducir.setHeight_m(cur.get("height_m").toString());
			pokemonAIntroducir.setHp(cur.get("hp").toString());
			pokemonAIntroducir.setJapanese_name(cur.get("japanese_name").toString());
			pokemonAIntroducir.setName(cur.get("name").toString());
			pokemonAIntroducir.setPercentage_male(cur.get("percentage_male").toString());
			pokemonAIntroducir.setPokedex_number(cur.get("pokedex_number").toString());
			pokemonAIntroducir.setSp_attack(cur.get("sp_attack").toString());
			pokemonAIntroducir.setSp_defense(cur.get("sp_defense").toString());
			pokemonAIntroducir.setSpeed(cur.get("speed").toString());
			pokemonAIntroducir.setType1(cur.get("type1").toString());
			pokemonAIntroducir.setType2(cur.get("type2").toString());
			pokemonAIntroducir.setWeight_kg(cur.get("weight_kg").toString());
			pokemonAIntroducir.setGeneration(cur.get("generation").toString());
			pokemonAIntroducir.setIs_legendary(cur.get("is_legendary").toString());

			listaPokemonCompleta.add(pokemonAIntroducir);
		}

		System.out.println("El tamaño de la lista es " + listaPokemonCompleta.size());
		
		return "principal";
	}

	@PostMapping("/filtrar")
	public String controladorFiltro(Model model, @RequestParam String generacion, @RequestParam String tipo1,
			@RequestParam String ordenar,
			@RequestParam(value = "legendario", required = false) String checkBoxLegendario) {

		List<Pokemon> listaPokemonConFiltro = new ArrayList<Pokemon>();

		if (checkBoxLegendario == null)
			checkBoxLegendario = "0";
		else
			checkBoxLegendario = "1";

		System.out.println("Generacion: " + generacion);
		System.out.println("Tipo: " + tipo1);
		System.out.println("Ordenar: " + ordenar);
		System.out.println("Legendario: " + checkBoxLegendario); // null si no esta marcado y on si esta marcado

		// Solo hay que filtrar por legendario o no
		if (generacion.equals("todasgeneraciones") && tipo1.equals("todostipos")) {

			for (int i = 0; i < listaPokemonCompleta.size(); i++) {
				if (listaPokemonCompleta.get(i).getIs_legendary().equals(checkBoxLegendario))
					listaPokemonConFiltro.add(listaPokemonCompleta.get(i));
			}

		} else if (generacion.equals("todasgeneraciones") || tipo1.equals("todostipos")) {

			// Filtrar por tipo y legendarios
			if (generacion.equals("todasgeneraciones")) {

				for (int i = 0; i < listaPokemonCompleta.size(); i++) {
					if ((listaPokemonCompleta.get(i).getType1().equals(tipo1)
							|| listaPokemonCompleta.get(i).getType2().equals(tipo1))
							&& listaPokemonCompleta.get(i).getIs_legendary().equals(checkBoxLegendario)) {
						listaPokemonConFiltro.add(listaPokemonCompleta.get(i));
					}
				}
			}
			// Filtrar por generacion y legendario
			else {
				for (int i = 0; i < listaPokemonCompleta.size(); i++) {
					if (listaPokemonCompleta.get(i).getGeneration().equals(generacion)
							&& listaPokemonCompleta.get(i).getIs_legendary().equals(checkBoxLegendario)) {
						listaPokemonConFiltro.add(listaPokemonCompleta.get(i));
					}
				}
			}
		} else {

			// Filtro por generacion tipo y legendario
			for (int i = 0; i < listaPokemonCompleta.size(); i++) {
				if (listaPokemonCompleta.get(i).getGeneration().equals(generacion)
						&& (listaPokemonCompleta.get(i).getType1().equals(tipo1)
								|| listaPokemonCompleta.get(i).getType2().equals(tipo1))
						&& listaPokemonCompleta.get(i).getIs_legendary().equals(checkBoxLegendario)) {
					listaPokemonConFiltro.add(listaPokemonCompleta.get(i));
				}
			}
		}
		
		//Ordenamos en funcion del valor del parametro ordenar: Ascendente o Descendente en funcion de su numero de pokedex
		if (ordenar.equals("Ascendente")) {

			listaPokemonConFiltro.sort(new Comparator<Pokemon>() {
				@Override
				public int compare(Pokemon p1, Pokemon p2) {
					if (Integer.parseInt(p1.getPokedex_number()) > Integer.parseInt(p2.getPokedex_number()))
						return 1;
					if (Integer.parseInt(p1.getPokedex_number()) < Integer.parseInt(p2.getPokedex_number()))
						return -1;
					return 0;
				}
			});

		}
		else {
			listaPokemonConFiltro.sort(new Comparator<Pokemon>() {
				@Override
				public int compare(Pokemon p1, Pokemon p2) {
					if (Integer.parseInt(p1.getPokedex_number()) < Integer.parseInt(p2.getPokedex_number()))
						return 1;
					if (Integer.parseInt(p1.getPokedex_number()) > Integer.parseInt(p2.getPokedex_number()))
						return -1;
					return 0;
				}
			});
		}

		model.addAttribute("pokemons", listaPokemonConFiltro);
		
		return "filtro";

	}

	@PostMapping("/nuevoPokemon")
	public String controladorAñador(Model model, @RequestParam String name, @RequestParam String abilities, @RequestParam String against_bug,
			@RequestParam String against_dark, @RequestParam String against_dragon,
			@RequestParam String against_electric, @RequestParam String against_fairy,
			@RequestParam String against_fight, @RequestParam String against_fire, @RequestParam String against_flying,
			@RequestParam String against_ghost, @RequestParam String against_grass, @RequestParam String against_ground,
			@RequestParam String against_ice, @RequestParam String against_normal, @RequestParam String against_poison,
			@RequestParam String against_psychic, @RequestParam String against_rock, @RequestParam String against_steel,
			@RequestParam String against_water, @RequestParam String attack, @RequestParam String base_egg_steps,
			@RequestParam String base_happiness, @RequestParam String base_total, @RequestParam String capture_rate,
			@RequestParam String classfication, @RequestParam String defense, @RequestParam String experience_growth,
			@RequestParam String height_m, @RequestParam String hp, @RequestParam String japanese_name,
			@RequestParam String percentage_male, @RequestParam String pokedex_number, @RequestParam String sp_attack,
			@RequestParam String sp_defense, @RequestParam String speed, @RequestParam String type1,
			@RequestParam String type2, @RequestParam String weight_kg, @RequestParam String generation,
			@RequestParam String is_legendary) {

		System.out.println("El nombre del pokemon introducido es : " + name);

		model.addAttribute("nombrePokemon", name);
		
		//Cogemos el modelo para tener un documento con los mismos campos y vamos reemplazando
		
		Document pokemonAInsertar = collection.find().first();
		
		//Insertamos los campos del pokemon nuevo
		pokemonAInsertar.remove("_id");
		pokemonAInsertar.put("name", name);
		pokemonAInsertar.put("abilities", abilities);
		pokemonAInsertar.put("against_bug", against_bug);
		pokemonAInsertar.put("against_dark", against_dark);
		pokemonAInsertar.put("against_dragon", against_dragon);
		pokemonAInsertar.put("against_electric", against_electric);
		pokemonAInsertar.put("against_fairy", against_fairy);
		pokemonAInsertar.put("against_fight", against_fight);
		pokemonAInsertar.put("against_fire", against_fire);
		pokemonAInsertar.put("against_flying", against_flying);
		pokemonAInsertar.put("against_ghost", against_ghost);
		pokemonAInsertar.put("against_grass", against_grass);
		pokemonAInsertar.put("against_ground", against_ground);
		pokemonAInsertar.put("against_ice", against_ice);
		pokemonAInsertar.put("against_normal", against_normal);
		pokemonAInsertar.put("against_poison", against_poison);
		pokemonAInsertar.put("against_psychic", against_psychic);
		pokemonAInsertar.put("against_rock", against_rock);
		pokemonAInsertar.put("against_steel", against_steel);
		pokemonAInsertar.put("against_water", against_bug);
		pokemonAInsertar.put("attack", attack);
		pokemonAInsertar.put("base_egg_steps", base_egg_steps);
		pokemonAInsertar.put("base_happiness", base_happiness);
		pokemonAInsertar.put("base_total", base_total);
		pokemonAInsertar.put("capture_rate", capture_rate);
		pokemonAInsertar.put("classfication", classfication);
		pokemonAInsertar.put("defense", defense);
		pokemonAInsertar.put("experience_growth", experience_growth);
		pokemonAInsertar.put("height_m", height_m);
		pokemonAInsertar.put("hp", hp);
		pokemonAInsertar.put("japanese_name", japanese_name);
		pokemonAInsertar.put("percentage_male", percentage_male);
		pokemonAInsertar.put("pokedex_number", pokedex_number);
		pokemonAInsertar.put("sp_attack", sp_attack);
		pokemonAInsertar.put("sp_defense", sp_defense);
		pokemonAInsertar.put("speed", speed);
		pokemonAInsertar.put("type1", type1);
		pokemonAInsertar.put("type2", type2);
		pokemonAInsertar.put("weight_kg", weight_kg);
		pokemonAInsertar.put("generation", generation);
		pokemonAInsertar.put("is_legendary", is_legendary);
		
		
		printJson(pokemonAInsertar);
		
		collection.insertOne(pokemonAInsertar);
		
		
		
		return "nuevoPokemon";

	}
	
	/*
	 * Devuelve la informacion de un pokemon en concreto
	 */
	@GetMapping("/pokemon")
	public String pokemon (Model model, @RequestParam String name,HttpServletRequest request) {
		
		Pokemon pokemonAMostrar = new Pokemon();
		for(int i = 0; i<listaPokemonCompleta.size();i++) {
			if (listaPokemonCompleta.get(i).getName().equals(name))
				pokemonAMostrar = listaPokemonCompleta.get(i);
		}
		
		model.addAttribute("pokemon", pokemonAMostrar);
		
		System.out.println("El nombre del pokemon es: " + name);
		
		return "pokemon";
	}
	
	@GetMapping("/borrarPokemon")
	public String borrarPokemon (Model model, @RequestParam String name,HttpServletRequest request) {
		
		
		System.out.println("El nombre del pokemon a borrar es: " + name);
		
		
		collection.deleteOne(eq("name", name));
		
		
		return "borrarPokemon";
	}
	
	
}
	
	
	
	
