package com.example.curshach.service;

import com.example.curshach.dto.TagDto;
import com.example.curshach.mapper.TagMapper;
import com.example.curshach.model.Tag;
import com.example.curshach.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper mapper;

    public List<TagDto> getAll() {
        return mapper.toDTO(tagRepository.findAll());
    }

    public TagDto create(TagDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        return mapper.toDTO(tagRepository.save(tag));
    }

    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}
