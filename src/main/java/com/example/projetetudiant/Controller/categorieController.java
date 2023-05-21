package com.example.projetetudiant.Controller;

import com.example.projetetudiant.dao.categorieRepository;
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
public class categorieController {
    private categorieRepository categorieRepository;

    @GetMapping("/listeCategorie")
    public String listeCategorie(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "6") int size,
                                  @RequestParam(name = "key", defaultValue = "") String key
    ) {
        Page<categorie> categoriePage = categorieRepository.findAllByNomContains(key, PageRequest.of(page, size));
        model.addAttribute("pages", categoriePage.getContent());
        model.addAttribute("nbrPages", new int[categoriePage.getTotalPages()]);
        model.addAttribute("key", key);
        model.addAttribute("pageCurrent", page);
        return "listeCategorie";
    }


    @GetMapping("/createCategorie")
    public String createCategorie(Model model) {
        model.addAttribute("categorie", new categorie());
        return "createCategorie";
    }


    @PostMapping("/saveCat")
    public String saveCat(Model model, @Valid categorie categorie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createCategorie";
        }
        categorieRepository.save(categorie);
        return "redirect:/listeCategorie";
    }


    @GetMapping("/editeCategorie")
    public String editeCategorie(Model model, Long id, int page, String key) {
        Optional<categorie> categorieOptional = categorieRepository.findById(id);
        if (categorieOptional.isPresent()) {
            categorie categorie = categorieOptional.get();
            model.addAttribute("categorie", categorie);
            model.addAttribute("page", page);
            model.addAttribute("key", key);
            return "editeCategorie";
        } else {
            // Gérer le cas où la catégorie n'est pas trouvée
            return "redirect:/listeCategorie";
        }
    }

    @PostMapping("/editeCategorie")
    public String saveEditCat(Model model, @Valid categorie categorie, BindingResult bindingResult,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "key", defaultValue = "") String key) {
        if (bindingResult.hasErrors()) {
            return "editeCategorie";
        }
        categorieRepository.save(categorie);
        return "redirect:/listeCategorie?page=" + page + "&key=" + key;
    }


    @GetMapping(value = "/deleteCategorie")
    public String deleteCategorie(Long id, int page, String key) {

        categorieRepository.deleteById(id);
        return "redirect:/listeCategorie?page=" + page + "&key=" + key;
    }

}