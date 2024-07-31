package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.Images;
import com.slmas.Sl.domain.Ticket;
import com.slmas.Sl.dto.request.TicketRequestDto;
import com.slmas.Sl.dto.response.TicketResponseDto;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.repository.TicketRepository;
import com.slmas.Sl.service.TicketService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public String createTicket(TicketRequestDto ticketRequestDto, Images images) throws RepositoryException {
        Ticket ticket = MapDtoToTicket(ticketRequestDto);
        ticket.setImage(images.getFullImage());
        Integer res = ticketRepository.createTicket(ticket);
        if (res == 1) {
            return "Ticket creado con exito.";
        } else {
            return "Error al crear el ticket";
        }
    }

    @Override
    public TicketResponseDto getTicketById(Long id) throws RepositoryException {
        Ticket ticket = ticketRepository.getTicketById(id);
        return MapTicketToDto(ticket);
    }

    @Override
    public List<TicketResponseDto> getFilteredTickets(Date startDate, Date endDate, String area, String closed) throws RepositoryException {
        List<Ticket> tickets = ticketRepository.getFilteredTickets(startDate, endDate, area, closed);
        return tickets.stream().map(this::MapTicketToDto).collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> getUserTickets(Date startDate, Date endDate, Long userId) throws RepositoryException {
        List<Ticket> tickets = ticketRepository.getUserTickets(startDate, endDate, userId);
        return tickets.stream().map(this::MapTicketToDto).collect(Collectors.toList());
    }

    @Override
    public String closeTicket (TicketRequestDto ticketRequestDto) throws RepositoryException {
        Integer response = ticketRepository.closeTicket(MapDtoToTicket(ticketRequestDto));
        if (response == 1) {
            return "Ticket cerado con exito.";
        } else {
            throw new RuntimeException("Error al cerrar el ticket");
        }
    }

    private TicketResponseDto MapTicketToDto(Ticket ticket) {
        TicketResponseDto ticketResponseDto = new TicketResponseDto();
        ticketResponseDto.setId(ticket.getId());
        ticketResponseDto.setUserId(ticket.getUserId());
        ticketResponseDto.setArea(ticket.getArea());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(ticket.getDate());
        ticketResponseDto.setDate(formattedDate);
        ticketResponseDto.setTitle(ticket.getTitle());
        ticketResponseDto.setType(ticket.getType());
        ticketResponseDto.setDescription(ticket.getDescription());
        ticketResponseDto.setSolution(ticket.getSolution());
        ticketResponseDto.setSolvedBy(ticket.getSolvedBy());
        if (ticket.getSolvedDate() != null) {
            SimpleDateFormat solvedDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedSolvedDate = solvedDateFormat.format(ticket.getSolvedDate());
            ticketResponseDto.setSolvedDate(formattedSolvedDate);
        }
        ticketResponseDto.setImage(ticket.getImage());
        ticketResponseDto.setClosed(ticket.isClosed());
        ticketResponseDto.setUserName(ticket.getUserName());
        return ticketResponseDto;
    }

    private Ticket MapDtoToTicket(TicketRequestDto ticketRequestDto) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketRequestDto.getId());
        ticket.setUserId(ticketRequestDto.getUserId());
        ticket.setUserName(ticketRequestDto.getUserName());
        ticket.setArea(ticketRequestDto.getArea());
        ticket.setDate(new Date());
        ticket.setTitle(ticketRequestDto.getTitle());
        ticket.setType(ticketRequestDto.getType());
        ticket.setDescription(ticketRequestDto.getDescription());
        ticket.setSolution(ticketRequestDto.getSolution());
        ticket.setSolvedBy(ticketRequestDto.getSolvedBy());
        ticket.setSolvedDate(ticketRequestDto.getSolvedDate());
        ticket.setClosed(ticketRequestDto.isClosed());
        return ticket;
    }
}
