package com.example.projetetudiant.Controller;

import com.example.projetetudiant.dao.categorieRepository;
import com.example.projetetudiant.entities.Produit;
import com.example.projetetudiant.dao.ProduitRepository;
import com.example.projetetudiant.entities.categorie;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ProduitController {
   private ProduitRepository produitRepository;
   private categorieRepository categorieRepository;
    @GetMapping("/listeProduits")
    public String listeProduits(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "6") int size,
                       @RequestParam(name = "key",defaultValue = "") String key
    ){
        Page<Produit> produitPage=produitRepository.findAllByNomContains(key, PageRequest.of(page,size));
        model.addAttribute("pages",produitPage.getContent());
        model.addAttribute("nbrPages",new int[produitPage.getTotalPages()]);
        model.addAttribute("key",key);
        model.addAttribute("pageCurrent",page);
        return "listeProduits";
    }



    @GetMapping("/createProduit")
    public String createProduit(Model model) {
        model.addAttribute("produit", new Produit());
        model.addAttribute("categories", categorieRepository.findAll());
        return "createProduit";
    }

    @PostMapping("/saveProduit")
    public String saveProduit(@Valid Produit produit, BindingResult bindingResult,
                              @RequestParam(name = "categorie", required = false) Long categorieId) {
        if (bindingResult.hasErrors()) {
            return "createProduit";
        }

        if (categorieId != null) {
            Optional<categorie> optionalCategorie = categorieRepository.findById(categorieId);
            optionalCategorie.ifPresent(produit::setCategorie);
        }

        produitRepository.save(produit);
        return "redirect:/listeProduits";
    }


    @GetMapping("/editeProduit")
    public String editeProduit(Model model, Long id, int page, String key) {
        Optional<Produit> produitOptional = produitRepository.findById(id);
        if (produitOptional.isPresent()) {
            Produit produit = produitOptional.get();
            // Ensure that the categorie property is not null
            if (produit.getCategorie() == null) {
                produit.setCategorie(new categorie()); // Create a new Categorie instance if it's null
            }
            model.addAttribute("produit", produit);
            model.addAttribute("page", page);
            model.addAttribute("key", key);
            model.addAttribute("categories", categorieRepository.findAll());
            return "editeProduit";
        } else {
            // Gérer le cas où le produit n'est pas trouvé
            return "redirect:/listeProduits";
        }
    }



    @PostMapping("/editeProduit")
    public String saveProduit(Model model, @Valid Produit produit, BindingResult bindingResult,
                              @RequestParam(name = "idCategorie", required = false) Long idCategorie,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "key", defaultValue = "") String key) {
        if (bindingResult.hasErrors()) {
            return "editeProduit";
        }
        if (idCategorie != null) {
            Optional<categorie> optionalCategorie = categorieRepository.findById(idCategorie);
            optionalCategorie.ifPresent(produit::setCategorie);
        }

        produitRepository.save(produit);
        return "redirect:/listeProduits?page=" + page + "&key=" + key;
    }

    @GetMapping(value = "/delete")
    public String delete(Long id,int page, String key){

        produitRepository.deleteById(id);
        return "redirect:/listeProduits?page="+page+ "&key=" +key;
    }



}
