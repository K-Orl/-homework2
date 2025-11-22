package org.example.service;

import org.example.User;
import org.example.UserDto;
import org.example.UserRepository;
import org.example.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(
                new User("Alice", "alice@mail.com", 25),
                new User("Bob", "bob@mail.com", 30)
        ));

        List<UserDto> users = userService.getAllUsers();
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testCreateUser() {
        UserDto dto = new UserDto(null, "Charlie", "charlie@mail.com", 28, null);
        User user = new User("Charlie", "charlie@mail.com", 28);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto created = userService.createUser(dto);
        assertEquals("Charlie", created.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserById() {
        User user = new User("Anna", "anna@mail.com", 22);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto found = userService.getUserById(1L);
        assertNotNull(found);
        assertEquals("Anna", found.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        User existing = new User("Alice", "alice@mail.com", 25);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenReturn(existing);

        UserDto updateDto = new UserDto(null, "AliceUpdated", "alice2@mail.com", 26, null);
        UserDto updated = userService.updateUser(1L, updateDto);

        assertEquals("AliceUpdated", updated.getName());
        assertEquals(26, updated.getAge());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existing);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
