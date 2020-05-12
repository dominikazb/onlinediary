package com.dominikazb.controllers;

import com.dominikazb.beans.Category;
import com.dominikazb.beans.Note;
import com.dominikazb.beans.Tag;
import com.dominikazb.beans.User;
import com.dominikazb.repositories.CategoryRepository;
import com.dominikazb.repositories.NoteRepository;
import com.dominikazb.repositories.TagRepository;
import com.dominikazb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("currentusername")
public class DiaryController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, "date", new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }


    @ModelAttribute("currentusername")
    public String usernameOfCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameOfTheCurrentUser = authentication.getName();
        return usernameOfTheCurrentUser;
    }

    @GetMapping("/diary")
    public String goToDiary(@ModelAttribute("currentusername") String username, Model model) {

        String usernameOfTheCurrentUser = (String) model.getAttribute("currentusername");
        model.addAttribute("currentUsername", usernameOfTheCurrentUser);

        //get the notes of the current user
        List<Note> listOfNotesForTheCurrentUser = noteRepository.findAllByUserAndOrderByDate(usernameOfTheCurrentUser);
        model.addAttribute("listOfNotesForTheCurrentUser", listOfNotesForTheCurrentUser);

        //get categories of the current user
        Set<Category> listOfCategoriesForTheCurrentUser = userRepository.findCategoriesByUser(usernameOfTheCurrentUser);
        model.addAttribute("listOfCategoriesForTheCurrentUser", listOfCategoriesForTheCurrentUser);

        //get all tags of the current user
        Set<Tag> listOfTagsForTheCurrentUser = userRepository.findTagsByUser(usernameOfTheCurrentUser);
        model.addAttribute("listOfTagsForTheCurrentUser", listOfTagsForTheCurrentUser);

        return "diary";
    }

    @GetMapping("/addnote")
    public String singleBlogNote(Model model) {
        model.addAttribute("note", new Note());
        return "addnote";
    }


    @PostMapping("/addnote")
    public String addNote(@ModelAttribute Note note, Model model) {

        String usernameOfTheCurrentUser = (String) model.getAttribute("currentusername");
        Optional<User> currentUserOpt = userRepository.findByUsername(usernameOfTheCurrentUser);
        User currentUser = currentUserOpt.get();

        //date
        Date date = note.getDate();
        if(note.getDate() == null) {
            date = Calendar.getInstance().getTime();
        }

        //title
        String title = note.getTitle();
        if(title.isEmpty()) {
            title = "Title";
        }

        //content
        String content = note.getContent();
        if(content.isEmpty()) {
            content = "Nothing written here :)";
        }


        //categories
        String categoriesFromInputString = note.getCategoriesString();
        Set<Category> listOfCategoriesForTheCurrentNote = getCategoriesFromInputAndConvertThemIntoSet(note);

        //tags
        String tagsFromInputString = note.getTagsString();
        Set<Tag> listOfTagsForTheCurrentNote = getTagsFromInputAndConvertThemIntoSet(note);

        int noteId = note.getId();

        if(noteId != 0) {
           Set<Category> listOfCategoriesForTheCurrentNote2 = getCategoriesFromInputAndConvertThemIntoSet(note);
           Set<Tag> listOfTagsForTheCurrentNote2 = getTagsFromInputAndConvertThemIntoSet(note);
           Note updatedNote = new Note(noteId, note.getDate(), note.getTitle(), note.getContent(), note.getCategoriesString(),
                   note.getTagsString(), listOfCategoriesForTheCurrentNote, listOfTagsForTheCurrentNote, currentUser);
           noteRepository.save(updatedNote);
        }

        if(noteId == 0) {
            Note currentNote = new Note(date, title, content, categoriesFromInputString, tagsFromInputString,
                    listOfCategoriesForTheCurrentNote, listOfTagsForTheCurrentNote, currentUser);
            noteRepository.save(currentNote);
        }

        return "redirect:/diary";
    }

    @GetMapping("/edit/{noteId}")
    public String getEditedNote(@PathVariable Integer noteId, Model model) {

        String usernameOfTheCurrentUser = (String) model.getAttribute("currentusername");
        List<Note> listOfNotesForTheCurrentUser = noteRepository.findAllByUserAndOrderByDate(usernameOfTheCurrentUser);

        String returnAddress = "";
        for (Note searchedNote : listOfNotesForTheCurrentUser) {
            if(searchedNote.getId() != noteId) {
                returnAddress = "sorry";
            }

            if(searchedNote.getId() == noteId) {
                Optional<Note> noteOpt = noteRepository.findById(noteId);
                Note note = noteOpt.get();
                model.addAttribute("note", note);
                returnAddress = "addnote";
                break;
            }
        }

        return returnAddress;
    }


    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId) {
        Optional<Note> noteOpt = noteRepository.findById(noteId);
        noteRepository.deleteNote(noteId);
        return "redirect:/diary";
    }



    //methods
    public Set<Category> getCategoriesFromInputAndConvertThemIntoSet(Note note) {

        Set<Category> listOfCategoriesForTheCurrentNote = new HashSet<>();
        String categoriesFromInputString = note.getCategoriesString();
        List<Category> listOfCategoriesFromDatabase = categoryRepository.findAll();
        String[] categoriesFromInput = categoriesFromInputString.split(",");
        List<Category> listOfCategoriesInputByUserButNotPresentInDatabase = new ArrayList<>();
        List<Category> listOfCategoriesInputByUserAndPresentInDatabase = new ArrayList<>();

        for(Category category : listOfCategoriesFromDatabase) {
            for(int i=0; i<categoriesFromInput.length; i++) {
                String categoryName = categoriesFromInput[i];
                if(category.getCategoryName().equals(categoryName)) {
                    listOfCategoriesInputByUserAndPresentInDatabase.add(category);
                    listOfCategoriesForTheCurrentNote.add(category);
                }
            }
        }

        for(String categoryNameFromInputField : categoriesFromInput) {
            Category newCategory = new Category(categoryNameFromInputField);
            listOfCategoriesInputByUserButNotPresentInDatabase.add(newCategory);
            for (int i = 0; i < listOfCategoriesInputByUserAndPresentInDatabase.size(); i++) {
                String categoryNameWhichIsAlreadyPresentInDatabase = listOfCategoriesInputByUserAndPresentInDatabase.get(i).getCategoryName();
                if(categoryNameFromInputField.equals(categoryNameWhichIsAlreadyPresentInDatabase)) {
                    listOfCategoriesInputByUserButNotPresentInDatabase.remove(newCategory);
                }
            }
        }

        for(Category category : listOfCategoriesInputByUserButNotPresentInDatabase) {
            categoryRepository.save(category);
            listOfCategoriesForTheCurrentNote.add(category);
        }

        return listOfCategoriesForTheCurrentNote;
    }

    public Set<Tag> getTagsFromInputAndConvertThemIntoSet(Note note) {

        Set<Tag> listOfTagsForTheCurrentNote = new HashSet<>();
        List<Tag> listOfTagsFromDatabase = tagRepository.findAll();
        String tagsFromInputString = note.getTagsString();
        String[] tagsFromInput = tagsFromInputString.split(",");
        List<Tag> listOfTagsInputByUserButNotPresentInDatabase = new ArrayList<>();
        List<Tag> listOfTagsInputByUserAndPresentInDatabase = new ArrayList<>();

        for(Tag tag : listOfTagsFromDatabase) {
            for(int i=0; i<tagsFromInput.length; i++) {
                String tagName = tagsFromInput[i];
                if(tag.getTagName().equals(tagName)) {
                    listOfTagsInputByUserAndPresentInDatabase.add(tag);
                    listOfTagsForTheCurrentNote.add(tag);
                }
            }
        }

        for(String tagNameFromInputField : tagsFromInput) {
            Tag newTag = new Tag(tagNameFromInputField);
            listOfTagsInputByUserButNotPresentInDatabase.add(newTag);
            for (int i = 0; i < listOfTagsInputByUserAndPresentInDatabase.size(); i++) {
                String tagNameWhichIsAlreadyPresentInDatabase = listOfTagsInputByUserAndPresentInDatabase.get(i).getTagName();
                if(tagNameFromInputField.equals(tagNameWhichIsAlreadyPresentInDatabase)) {
                    listOfTagsInputByUserButNotPresentInDatabase.remove(newTag);
                }
            }
        }

        for(Tag tag : listOfTagsInputByUserButNotPresentInDatabase) {
            tagRepository.save(tag);
            listOfTagsForTheCurrentNote.add(tag);
        }

        return listOfTagsForTheCurrentNote;
    }

}