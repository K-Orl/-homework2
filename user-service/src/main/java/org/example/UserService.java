package org.example;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // Создать пользователя
    public UserDto createUser(UserDto dto) {
        User user = new User(dto.getName(), dto.getEmail(), dto.getAge());
        User saved = repository.save(user);
        return mapToDto(saved);
    }

    // Получить всех пользователей
    public List<UserDto> getAllUsers() {
        return repository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Получить пользователя по ID
    public UserDto getUserById(Long id) {
        return repository.findById(id).map(this::mapToDto).orElse(null);
    }

    // Обновить пользователя
    public UserDto updateUser(Long id, UserDto dto) {
        return repository.findById(id).map(user -> {
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setAge(dto.getAge());
            return mapToDto(repository.save(user));
        }).orElse(null);
    }

    // Удалить пользователя
    public boolean deleteUser(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // Преобразование User -> UserDto
    private UserDto mapToDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getAge());
    }
}
