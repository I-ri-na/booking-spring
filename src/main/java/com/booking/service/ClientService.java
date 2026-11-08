package com.booking.service;

import com.booking.entity.Client;
import com.booking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<Client> getAllClients() {
        return clientRepository.findAll(Sort.by("id"));
    }

    public Client addClient(Client data) {
        normalizeAndValidate(data);
        checkUnique(data.getPhone(), data.getEmail(), null);

        Client client = new Client();
        client.setName(data.getName());
        client.setPhone(data.getPhone());
        client.setEmail(data.getEmail());
        return clientRepository.saveAndFlush(client);
    }

    public Client updateClient(Integer id, Client data) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Клиент с id " + id + " не найден"));

        normalizeAndValidate(data);
        checkUnique(data.getPhone(), data.getEmail(), id);

        existing.setName(data.getName());
        existing.setPhone(data.getPhone());
        existing.setEmail(data.getEmail());
        return clientRepository.saveAndFlush(existing);
    }

    public void deleteClient(Integer id) {
        // брони удалятся каскадом в бд
        clientRepository.deleteById(id);
    }

    private void normalizeAndValidate(Client c) {
        c.setName(trim(c.getName()));
        c.setPhone(trim(c.getPhone()));
        c.setEmail(trim(c.getEmail()));
        if (c.getName().isEmpty() || c.getPhone().isEmpty() || c.getEmail().isEmpty()) {
            throw new BusinessException("Заполните имя, телефон и email");
        }
    }

    private void checkUnique(String phone, String email, Integer excludeId) {
        boolean phoneTaken = excludeId == null
                ? clientRepository.existsByPhone(phone)
                : clientRepository.existsByPhoneAndIdNot(phone, excludeId);
        if (phoneTaken) {
            throw new BusinessException("Клиент с телефоном " + phone + " уже существует");
        }

        boolean emailTaken = excludeId == null
                ? clientRepository.existsByEmail(email)
                : clientRepository.existsByEmailAndIdNot(email, excludeId);
        if (emailTaken) {
            throw new BusinessException("Клиент с email " + email + " уже существует");
        }
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
