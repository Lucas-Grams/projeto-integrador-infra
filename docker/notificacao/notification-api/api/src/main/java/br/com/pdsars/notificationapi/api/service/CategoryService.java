package br.com.pdsars.notificationapi.api.service;

import br.com.pdsars.notificationapi.api.dto.CategoryDTO;
import br.com.pdsars.notificationapi.api.model.Client;
import br.com.pdsars.notificationapi.api.repository.CategoryRepository;
import br.com.pdsars.notificationapi.api.repository.ClientRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoryService {
    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;

    public CategoryService(
        ClientRepository clientRepository,
        CategoryRepository categoryRepository
    ) {
        this.clientRepository = clientRepository;
        this.categoryRepository = categoryRepository;
    }

    @Nonnull
    public List<CategoryDTO> listCategories(@Nonnull String oauthClientId) {
        return this.clientRepository.findByOauthClientId(oauthClientId)
            .map(Client::getCategories)
            .orElse(List.of())
            .stream()
            .map(CategoryDTO::new)
            .toList();
    }
}
