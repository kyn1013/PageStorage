package com.example.PageStorage.membergenre.controller;

import com.example.PageStorage.membergenre.service.MemberGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/membergenres")
public class MemberGenreController {

    private final MemberGenreService memberGenreService;


}
