package com.slmas.Sl.controller;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slmas.Sl.domain.Images;
import com.slmas.Sl.dto.request.TicketRequestDto;
import com.slmas.Sl.dto.response.TicketResponseDto;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.service.TicketService;
import com.slmas.Sl.utils.ImageCompressor;
import com.slmas.Sl.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    TicketService ticketService;
    @Autowired
    private SimpMessagingTemplate template;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> CreateTicketHandler(@RequestParam(value = "file", required = false) MultipartFile file,
                                                 @RequestParam("ticket") String ticketJson) {
        try {
            TicketRequestDto ticketRequestDto = new ObjectMapper().readValue(ticketJson, TicketRequestDto.class);
            ValidationUtil.validate(ticketRequestDto, TicketRequestDto.Create.class);
            Images images = new Images();
            if (file != null && !file.isEmpty()) {
                images = ImageCompressor.compressImage(file.getBytes());
            }
            String res = ticketService.createTicket(ticketRequestDto, images);
            template.convertAndSend("/topic/notifications", "Nuevo ticket creado!");
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getFilteredTickets")
    public ResponseEntity<?> GetFilteredTicketsHandler(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                       @RequestParam String area,
                                                       @RequestParam String closed) {
        try {
            List<TicketResponseDto> tickets = ticketService.getFilteredTickets(startDate, endDate, area, closed);
            return ResponseEntity.status(HttpStatus.OK).body(tickets);

        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getUserTickets")
    public ResponseEntity<?> GetUsersTicketsHandler(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                    @RequestParam Long userId) {
        try {
            List<TicketResponseDto> tickets = ticketService.getUserTickets(startDate, endDate, userId);
            return ResponseEntity.status(HttpStatus.OK).body(tickets);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getTicket")
    public ResponseEntity<?> GetTicketByIdHandler(@RequestParam Long ticketId) {
        try {
            TicketResponseDto ticket = ticketService.getTicketById(ticketId);
            return ResponseEntity.status(HttpStatus.OK).body(ticket);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/close")
    public ResponseEntity<?> closeTicketHandler(@RequestBody TicketRequestDto ticketRequestDto) {
        System.out.println(ticketRequestDto.toString());
        try {
            String response = ticketService.closeTicket(ticketRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
