package com.example.demo;

import static util.Helpers.printJson;

import java.util.ArrayList;
import java.util.List;

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

	List<Pokemon> listaPokemonCompleta = new ArrayList<Pokemon>();

	@GetMapping("/")
	public String controladorInicio(Model model, HttpServletRequest request) {
		// db.InsertarArticulo();

		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase("pokemonDB"); // Nombre de la base de datos
		MongoCollection<Document> collection = database.getCollection("pokemonC"); // Nombre de la coleccion

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
		model.addAttribute("pokemons", listaPokemonCompleta);

		/*
		 * System.out.println("Find all with iteration: "); MongoCursor<Document> cursor
		 * = collection.find().iterator(); try { while (cursor.hasNext()) { Document cur
		 * = cursor.next(); printJson(cur); } } finally { cursor.close(); }
		 * 
		 * System.out.println("Count:"); long count = collection.count();
		 * System.out.println(count);
		 */

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

		}
		else if (generacion.equals("todasgeneraciones") || tipo1.equals("todostipos")) {

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
		model.addAttribute("pokemons", listaPokemonConFiltro);

		return "prueba";

	}

	@GetMapping("/legendarios")
	public String controladorLegendarios(Model model, HttpServletRequest request) {

		List<Pokemon> listaPokemonLegendarios = new ArrayList<Pokemon>();

		for (int i = 0; i < listaPokemonCompleta.size(); i++) {
			if (listaPokemonCompleta.get(i).getIs_legendary().equals("1")) {
				listaPokemonLegendarios.add(listaPokemonCompleta.get(i));
			}
		}

		model.addAttribute("pokemons", listaPokemonLegendarios);

		return "prueba";
	}

	@GetMapping("/generacion")
	public String controladorGeneraciones(Model model, HttpServletRequest request) {

		List<Pokemon> listaPokemonPorGeneracion = new ArrayList<Pokemon>();

		String generacion = "1";

		for (int i = 0; i < listaPokemonCompleta.size(); i++) {
			if (listaPokemonCompleta.get(i).getGeneration().equals(generacion)) {
				listaPokemonPorGeneracion.add(listaPokemonCompleta.get(i));
			}
		}

		model.addAttribute("pokemons", listaPokemonPorGeneracion);

		return "prueba";
	}

	@GetMapping("/tipo")
	public String controladorTipo(Model model, HttpServletRequest request) {

		List<Pokemon> listaPokemonPorTipo = new ArrayList<Pokemon>();

		String tipo = "grass";

		for (int i = 0; i < listaPokemonCompleta.size(); i++) {
			if (listaPokemonCompleta.get(i).getType1().equals(tipo)
					|| listaPokemonCompleta.get(i).getType2().equals(tipo)) {
				listaPokemonPorTipo.add(listaPokemonCompleta.get(i));
			}
		}

		model.addAttribute("pokemons", listaPokemonPorTipo);

		return "prueba";
	}

}
