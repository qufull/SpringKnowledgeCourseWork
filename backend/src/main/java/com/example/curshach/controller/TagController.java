package com.example.curshach.controller;

import com.example.curshach.dto.TagDto;
import com.example.curshach.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public List<TagDto> getAll() {
        return tagService.getAll();
    }

    @PostMapping
    public TagDto create(@RequestBody TagDto dto) {
        return tagService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }
}
