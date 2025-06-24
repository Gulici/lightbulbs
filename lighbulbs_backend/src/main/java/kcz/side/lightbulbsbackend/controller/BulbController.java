package kcz.side.lightbulbsbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kcz.side.lightbulbsbackend.model.BulbColor;
import kcz.side.lightbulbsbackend.model.SceneDto;
import kcz.side.lightbulbsbackend.service.BulbService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Bulbs", description = "Operations on remote light bulbs")
public class BulbController {

    private final BulbService bulbService;

    public BulbController(BulbService bulbService) {
        this.bulbService = bulbService;
    }

    @Operation(summary = "Turn on bulb by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Light bulb successfully turned on")
    })
    @PostMapping("/{id}/on")
    public ResponseEntity<Void> turnOn(@PathVariable int id) {
        bulbService.turnOn(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Turn off bulb by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Light bulb successfully turned off"),
            @ApiResponse(responseCode = "404", description = "Bulb not found")
    })
    @PostMapping("/{id}/off")
    public ResponseEntity<Void> turnOff(@PathVariable int id) {
        bulbService.turnOff(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Set RGB color and dimming for bulb by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Color set successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid RGB or dimming values"),
            @ApiResponse(responseCode = "404", description = "Bulb not found")
    })
    @PostMapping("/{id}/color")
    public ResponseEntity<Void> setColor(
            @PathVariable int id, @RequestBody
            @Validated({BulbColor.RGBGroup.class, BulbColor.DimGroup.class}) BulbColor color) {
        bulbService.setColor(id, color);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Set temperature and dimming for bulb by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temperature set successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid dimming value"),
            @ApiResponse(responseCode = "404", description = "Bulb not found")
    })
    @PostMapping("/{id}/temp")
    public ResponseEntity<Void> setTemp(
            @PathVariable int id,
            @RequestBody @Validated({BulbColor.DimGroup.class}) BulbColor color) {
        bulbService.setTemp(id, color);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Set predefined scene by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Scene applied successfully"),
            @ApiResponse(responseCode = "404", description = "Scene not found")
    })
    @PostMapping("/scene")
    public ResponseEntity<Void> setScene(@RequestBody @Valid SceneDto dto) {
        bulbService.setScene(dto.getId());
        return ResponseEntity.ok().build();
    }
}
