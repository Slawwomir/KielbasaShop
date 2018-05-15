package pl.KielbasaShop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.KielbasaShop.model.Kielbasa;
import pl.KielbasaShop.services.KielbasaService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/kielbasas")
public class KielbasaController {
    final KielbasaService kielbasaService;

    public KielbasaController(KielbasaService kielbasaService) {
        this.kielbasaService = kielbasaService;
    }

    @GetMapping
    public List<Kielbasa> listKielbasas(){
        return kielbasaService.findAll();
    }

    @PostMapping
    public ResponseEntity<Void> addKielbasa(@RequestBody Kielbasa kielbasa, UriComponentsBuilder uriBuilder){
        if(kielbasaService.find(kielbasa.getId()) == null){
            kielbasaService.save(kielbasa);
            URI location = uriBuilder.path("/kielbasas/{id}").buildAndExpand(kielbasa.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kielbasa> getKielbasa(@PathVariable UUID id){
        Kielbasa kielbasa = kielbasaService.find(id);
        return kielbasa != null ? ResponseEntity.ok(kielbasa) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateKielbasa(@RequestBody Kielbasa kielbasa){
        if(kielbasaService.find(kielbasa.getId()) != null){
            kielbasaService.save(kielbasa);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
