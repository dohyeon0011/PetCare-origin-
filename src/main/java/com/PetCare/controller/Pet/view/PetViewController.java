package com.PetCare.controller.Pet.view;

import com.PetCare.dto.Pet.response.PetResponse;
import com.PetCare.service.Pet.PetService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pets-care")
public class PetViewController {

    private final PetService petService;

    @Operation(description = "반려견 등록 || 수정")
    @GetMapping("/members/{customerId}/pets/new")
    public String newPet(@PathVariable("customerId") long customerId, Model model) {
        List<PetResponse.GetList> pets = petService.findById(customerId);

        if (pets.isEmpty()) {
            model.addAttribute("pets", new PetResponse.GetList());
        } else {
            model.addAttribute("pets", pets);
        }

        return "pet/newPet";
    }

    @Operation(description = "특정 회원의 모든 반려견 조회")
    @GetMapping("/members/{customerId}/pets")
    public String getAllPet(@PathVariable("customerId") long customerId, Model model) {
        List<PetResponse.GetList> pets = petService.findById(customerId);
        model.addAttribute("pets", pets);

        return "pet/petList";
    }

}
