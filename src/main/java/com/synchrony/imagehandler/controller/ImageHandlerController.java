import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.restapi.model.Tutorial;
import com.bezkoder.spring.restapi.service.TutorialService;

@CrossOrigin(
@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
  
    @Autowired
    ImageHandlerServiceImpl imageHandlerService;

  @GetMapping("/{imageId}")
  public ResponseEntity<Tutorial> getImagesByUserId(@PathVariable("userId") long userId) {
    Tutorial tutorial = tutorialService.findById(id);

    if (tutorial != null) {
      return new ResponseEntity<>(tutorial, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/")
  public ResponseEntity<Tutorial> uploadImages(@RequestBody Tutorial tutorial) {
    try {
      Tutorial _tutorial = tutorialService
          .save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
      return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{imageId}")
  public ResponseEntity<Tutorial> updateImages(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
    Tutorial _tutorial = tutorialService.findById(id);

    if (_tutorial != null) {
      _tutorial.setTitle(tutorial.getTitle());
      _tutorial.setDescription(tutorial.getDescription());
      _tutorial.setPublished(tutorial.isPublished());
      return new ResponseEntity<>( tutorialService.save(_tutorial), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{imageId}")
  public ResponseEntity<HttpStatus> deleteImageByUserId(@PathVariable("id") long id) {
    try {
      tutorialService.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/")
  public ResponseEntity<HttpStatus> deleteAllImagesByUserId() {
    try {
      tutorialService.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

}
