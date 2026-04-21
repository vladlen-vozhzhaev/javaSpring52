package com.example.demo.controller;

import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RoomController {
    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }
    @GetMapping("/addRoom")
    public String showAddRoom(Model model){
        model.addAttribute("room", new Room());
        return "addRoom";
    }
    @PostMapping("/addRoom")
    public String addRoom(@ModelAttribute Room room){
        room.setStatus("Свободен");
        roomRepository.save(room);
        return "redirect:/rooms";
    }
    @GetMapping("/rooms")
    public String showRooms(Model model){
        List<Room> roomList = roomRepository.findAll();
        model.addAttribute("roomList", roomList);
        return "room-list";
    }
    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id){
        roomRepository.deleteById(id);
        return "redirect:/rooms";
    }
    @GetMapping("/edit/{id}")
    public String updateRoom(@PathVariable Long id, Model model){
        Room room = roomRepository.findById(id).orElseThrow();
        model.addAttribute("room", room);
        return "edit-room";
    }
    @PostMapping("/edit/{id}")
    public String updateRoom(@PathVariable Long id, @ModelAttribute Room updatedRoom){
        Room room = roomRepository.findById(id).orElseThrow();
        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setType(updatedRoom.getType());
        room.setStatus(updatedRoom.getStatus());
        room.setPricePerNight(updatedRoom.getPricePerNight());
        roomRepository.save(room);
        return "redirect:/rooms";
    }
}
