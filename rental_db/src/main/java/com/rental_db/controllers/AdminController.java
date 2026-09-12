package com.rental_db.controllers;

import com.rental_db.dto.PropertyDTO;
import com.rental_db.dto.PropertyImageDTO;
import com.rental_db.dto.PropertyTypeDTO;
import com.rental_db.entities.Contact;
import com.rental_db.entities.Property;
import com.rental_db.entities.PropertyImage;
import com.rental_db.entities.PropertyType;
import com.rental_db.services.ContactService;
import com.rental_db.services.PropertyImageService;
import com.rental_db.services.PropertyService;
import com.rental_db.services.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PropertyService propertyService;
    private final PropertyTypeService propertyTypeService;
    private final PropertyImageService propertyImageService;
    private final ContactService contactService;

    @Autowired
    public AdminController(PropertyService propertyService, PropertyTypeService propertyTypeService, PropertyImageService propertyImageService, ContactService contactService) {
        this.propertyService = propertyService;
        this.propertyTypeService = propertyTypeService;
        this.propertyImageService = propertyImageService;
        this.contactService = contactService;
    }

    // --- Dashboard ---
    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Dashboard");
        
        List<Property> properties = propertyService.getAll();
        List<PropertyType> types = propertyTypeService.getAll();
        List<Contact> contacts = contactService.getAll();
        
        // Basic Stats
        model.addAttribute("totalProperties", properties.size());
        model.addAttribute("activeListings", properties.stream().filter(p -> Boolean.TRUE.equals(p.getAvailable())).count());
        model.addAttribute("totalTypes", types.size());
        model.addAttribute("totalContacts", contacts.size());
        
        // Financial Stats
        double totalValue = properties.stream().mapToDouble(Property::getPrice).sum();
        double averagePrice = properties.isEmpty() ? 0 : totalValue / properties.size();
        
        model.addAttribute("totalValue", totalValue);
        model.addAttribute("averagePrice", averagePrice);
        
        // Recent Properties (Last 5)
        List<Property> recentProperties = properties.stream()
                .sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
                .limit(5)
                .toList();
        model.addAttribute("recentProperties", recentProperties);
        
        // Chart 1: Properties by Type
        java.util.Map<String, Long> typeCounts = properties.stream()
                .filter(p -> p.getPropertyType() != null)
                .collect(java.util.stream.Collectors.groupingBy(p -> p.getPropertyType().getName(), java.util.stream.Collectors.counting()));
        
        model.addAttribute("typeLabels", typeCounts.keySet());
        model.addAttribute("typeData", typeCounts.values());

        // Chart 2: Contact Status Distribution
        long pendingContacts = contacts.stream().filter(c -> "pending".equals(c.getStatus())).count();
        long approvedContacts = contacts.stream().filter(c -> "approved".equals(c.getStatus())).count();
        long rejectedContacts = contacts.stream().filter(c -> "rejected".equals(c.getStatus())).count();
        
        model.addAttribute("contactStatusLabels", java.util.Arrays.asList("Pending", "Approved", "Rejected"));
        model.addAttribute("contactStatusData", java.util.Arrays.asList(pendingContacts, approvedContacts, rejectedContacts));

        // Chart 3: Availability Status
        long availableCount = properties.stream().filter(p -> Boolean.TRUE.equals(p.getAvailable())).count();
        long rentedCount = properties.size() - availableCount;
        
        model.addAttribute("availabilityLabels", java.util.Arrays.asList("Available", "Rented"));
        model.addAttribute("availabilityData", java.util.Arrays.asList(availableCount, rentedCount));

        // Chart 4: Properties by Location (Top 5)
        java.util.Map<String, Long> locationCounts = properties.stream()
                .filter(p -> p.getLocation() != null && !p.getLocation().isEmpty())
                .collect(java.util.stream.Collectors.groupingBy(Property::getLocation, java.util.stream.Collectors.counting()));
        
        java.util.List<java.util.Map.Entry<String, Long>> topLocations = locationCounts.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .toList();
        
        model.addAttribute("locationLabels", topLocations.stream().map(java.util.Map.Entry::getKey).toList());
        model.addAttribute("locationData", topLocations.stream().map(java.util.Map.Entry::getValue).toList());

        // Chart 5: Price Distribution
        long under1000 = properties.stream().filter(p -> p.getPrice() < 1000).count();
        long range1000to2000 = properties.stream().filter(p -> p.getPrice() >= 1000 && p.getPrice() < 2000).count();
        long range2000to3000 = properties.stream().filter(p -> p.getPrice() >= 2000 && p.getPrice() < 3000).count();
        long range3000to5000 = properties.stream().filter(p -> p.getPrice() >= 3000 && p.getPrice() < 5000).count();
        long above5000 = properties.stream().filter(p -> p.getPrice() >= 5000).count();
        
        model.addAttribute("priceRangeLabels", java.util.Arrays.asList("< $1000", "$1000-$2000", "$2000-$3000", "$3000-$5000", "> $5000"));
        model.addAttribute("priceRangeData", java.util.Arrays.asList(under1000, range1000to2000, range2000to3000, range3000to5000, above5000));

        return "admin/dashboard";
    }

    // --- Property Types ---
    @GetMapping("/property-types")
    public String listPropertyTypes(Model model) {
        model.addAttribute("pageTitle", "Property Types");
        model.addAttribute("types", propertyTypeService.getAll());
        return "admin/property-types";
    }

    @GetMapping("/property-types/add")
    public String addPropertyTypeForm(Model model) {
        model.addAttribute("pageTitle", "Add Property Type");
        model.addAttribute("type", new PropertyType());
        return "admin/add-property-type";
    }

    @GetMapping("/property-types/edit/{id}")
    public String editPropertyTypeForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Property Type");
        // Assuming we can find it, otherwise might need error handling
        PropertyType type = propertyTypeService.getAll().stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("type", type);
        return "admin/add-property-type";
    }

    @PostMapping("/property-types/save")
    public String savePropertyType(@ModelAttribute PropertyType type) {
        propertyTypeService.save(type);
        return "redirect:/admin/property-types";
    }

    @GetMapping("/property-types/delete/{id}")
    public String deletePropertyType(@PathVariable Long id) {
        propertyTypeService.delete(id);
        return "redirect:/admin/property-types";
    }

    // --- Properties ---
    @GetMapping("/properties")
    public String listProperties(Model model) {
        model.addAttribute("pageTitle", "Properties");
        model.addAttribute("properties", propertyService.getAll());
        return "admin/properties";
    }

    @GetMapping("/properties/add")
    public String addPropertyForm(Model model) {
        model.addAttribute("pageTitle", "Add Property");
        model.addAttribute("property", new Property());
        model.addAttribute("propertyTypes", propertyTypeService.getAll());
        return "admin/add-property";
    }

    @GetMapping("/properties/edit/{id}")
    public String editPropertyForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Property");
        Property property = propertyService.getAll().stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("property", property);
        model.addAttribute("propertyTypes", propertyTypeService.getAll());
        
        if (property != null) {
            model.addAttribute("images", propertyImageService.findByPropertyId(id));
        }
        
        return "admin/add-property";
    }

    @PostMapping("/properties/save")
    public String saveProperty(@ModelAttribute Property property, @RequestParam(required = false) String[] imageUrls) {
        // Save the property first
        Property savedProperty = propertyService.saveProperty(property);
        
        // Save image URLs if provided
        if (imageUrls != null && imageUrls.length > 0) {
            for (String imageUrl : imageUrls) {
                if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                    PropertyImageDTO imageDTO = new PropertyImageDTO();
                    imageDTO.setPropertyId(savedProperty.getId());
                    imageDTO.setImageUrl(imageUrl.trim());
                    propertyImageService.addImage(imageDTO);
                }
            }
        }
        
        return "redirect:/admin/properties";
    }

    @GetMapping("/properties/delete/{id}")
    public String deleteProperty(@PathVariable Long id) {
        propertyService.delete(id);
        return "redirect:/admin/properties";
    }

    // --- Property Images ---
    @PostMapping("/properties/{id}/images/add")
    public String addPropertyImage(@PathVariable Long id, @RequestParam("imageUrl") String imageUrl) {
        PropertyImageDTO dto = new PropertyImageDTO(id, imageUrl);
        propertyImageService.addImage(dto);
        return "redirect:/admin/properties/edit/" + id;
    }

    @GetMapping("/properties/images/delete/{id}")
    public String deletePropertyImage(@PathVariable Long id, @RequestParam("propertyId") Long propertyId) {
        propertyImageService.delete(id);
        return "redirect:/admin/properties/edit/" + propertyId;
    }

    // --- Contact Management ---
    @GetMapping("/contacts")
    public String listContacts(Model model) {
        model.addAttribute("pageTitle", "Contact Submissions");
        model.addAttribute("contacts", contactService.getAll());
        return "admin/contacts";
    }

    @GetMapping("/contacts/view/{id}")
    public String viewContact(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Contact Details");
        Contact contact = contactService.getById(id);
        model.addAttribute("contact", contact);
        return "admin/contact-details";
    }

    @PostMapping("/contacts/update-status/{id}")
    public String updateContactStatus(@PathVariable Long id, @RequestParam("status") String status) {
        contactService.updateStatus(id, status);
        return "redirect:/admin/contacts";
    }

    @GetMapping("/contacts/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.delete(id);
        return "redirect:/admin/contacts";
    }
}
