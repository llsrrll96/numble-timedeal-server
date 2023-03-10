package numble.server.timedeal.service;

import lombok.RequiredArgsConstructor;
import numble.server.timedeal.domain.category.Category;
import numble.server.timedeal.domain.category.CategoryDto;
import numble.server.timedeal.domain.category.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper  modelMapper = new ModelMapper();

    public CategoryDto categoryCreation(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getCategoryCode(),categoryDto.getCategoryName());
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Transactional
    public int deleteCategory(String categoryCode) {
        return categoryRepository.deleteByCategoryCode(categoryCode);
    }
}
