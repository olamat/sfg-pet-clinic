package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {
    
    public static final String LAST_NAME = "Smith";
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private PetTypeRepository petTypeRepository;
    
    @InjectMocks
    private OwnerSDJpaService service;
    private Owner returnOwner;
    
    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    }
    
    @Test
    void findByLastName() {
        //Given
        when(ownerRepository.findByLastName(anyString())).thenReturn(returnOwner);
        //When
        Owner smith = service.findByLastName(LAST_NAME);
        //Then
        verify(ownerRepository).findByLastName(anyString());
        assertEquals(LAST_NAME, smith.getLastName());
    }
    
    @Test
    void findAll() {
        Set<Owner> returnOwnerSet = Set.of(Owner.builder().id(1L).build(),
                                            Owner.builder().id(2L).build());
        when(ownerRepository.findAll()).thenReturn(returnOwnerSet);
        
        Set<Owner> owners = service.findAll();
        assertNotNull(owners);
        assertEquals(2, owners.size());
    }
    
    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
        Owner owner = service.findById(1L);
        assertNotNull(owner);
    }
    
    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner = service.findById(1L);
        assertNull(owner);
    }
    
    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(1L).build();
        when(ownerRepository.save(any())).thenReturn(returnOwner);
        Owner savedOwner = service.save(ownerToSave);
        assertNotNull(savedOwner);
        verify(ownerRepository).save(any());
    }
    
    @Test
    void delete() {
        service.delete(returnOwner);
        verify(ownerRepository).delete(any());
    }
    
    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(ownerRepository).deleteById(anyLong());
    }
}
