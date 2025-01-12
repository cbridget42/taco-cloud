package com.sia.tacocloud.controller;

import com.sia.tacocloud.entity.Taco;
import com.sia.tacocloud.repository.OrderRepository;
import com.sia.tacocloud.repository.TacoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/api/tacos", produces = "application/json")
@RequiredArgsConstructor
public class TacoController {

    private final TacoRepository tacoRepository;
    private final OrderRepository orderRepository;

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTaco() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        return tacoRepository.findById(id)
                .map(taco -> new ResponseEntity<>(taco, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/save", consumes = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTaco(@RequestBody Taco taco) {
        taco.setTacoOrder(orderRepository.findById(0L)
                .orElseThrow(() -> new NoSuchElementException("tech order not found")));
        taco.setTacoOrderKey(0L);

        tacoRepository.save(taco);
    }
}
