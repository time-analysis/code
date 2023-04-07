package repositories;

import filterCriteria.EntryFilterCriteria;
import Interfaces.DataAdapterInterface;
import de.models.Entry;
import ressourceModels.EntryRessource;
import ressourceModels.EntryRessourceStatus;
import ressourceModels.EntryRessourceType;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntryRepository implements EntryRepositoryInterface {
    private final DataAdapterInterface dataAdapter;
    private final String entryFileName;

    public EntryRepository(DataAdapterInterface dataAdapter, String entryFileName) {
        this.dataAdapter = dataAdapter;
        this.entryFileName = entryFileName;
    }

    @Override
    public void createEntry(Entry entry) {
        EntryRessource entryRessource = this.dataAdapter.mapEntryToEntryRessource(entry);
        String csvEntry = String.format(
                "%s,%s,%s,%s,%s,%s,%s"
                , sanitizeInputForCSVFormat(entryRessource.getLecture())
                , sanitizeInputForCSVFormat(entryRessource.getStart())
                , sanitizeInputForCSVFormat(entryRessource.getEnd())
                , sanitizeInputForCSVFormat(entryRessource.getType().name())
                , sanitizeInputForCSVFormat(entryRessource.getDetails())
                , entryRessource.getStatus()
                , entryRessource.getId()
        );
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(entryFileName, true))) {
            writer.write(csvEntry);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Entry> getEntrys() {
        List<EntryRessource> entryList;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(entryFileName))) {
            entryList = bufferedReader.lines().map(this::parseStringToEntryRessource).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.dataAdapter.mapEntryRessourceListToEntryList(entryList);
    }

    @Override
    public List<Entry> getEntrys(EntryFilterCriteria filterCriteria) {
        List<Entry> entries = getEntrys();
        Stream<Entry> entryStream = entries.stream();
        if (!Objects.isNull(filterCriteria.getEntryType())) {
            entryStream = entryStream.filter(entry -> entry.getType().name().equals(filterCriteria.getEntryType().name()));
        }
        if (!Objects.isNull(filterCriteria.getLectureName())) {
            entryStream = entryStream.filter(entry -> entry.getLecture().getName().equals(filterCriteria.getLectureName()));
        }
        if (!Objects.isNull(filterCriteria.getEntryStatus())) {
            entryStream = entryStream.filter(entry -> entry.getStatus().name().equals(filterCriteria.getEntryStatus().name()));
        }
        return entryStream.collect(Collectors.toList());
    }

    @Override
    public void udpateEntry(Entry entry) {
        List<Entry> entryList = getEntrys();

        Entry toReplace = entryList.stream().filter(e -> e.equals(entry)).findFirst().get();
        entryList.remove(toReplace);
        entryList.add(entry);
        try (PrintWriter writer = new PrintWriter(entryFileName)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Entry e : entryList) {
            createEntry(e);
        }
    }

    private String sanitizeInputForCSVFormat(String input) {
        String toReturn = input;
        if (!Objects.isNull(input)) {
            if (input.contains(",")) {
                System.out.println("The letter \",\" is not allowed as input. It will be removed"); //todo use uiplugin
                //SendUIMessageUseCase sendMessage = new SendUIMessageUesCase(uiPlugin);
                //sendMessage.send("no comma allowed");
                toReturn = toReturn.replace(",", "");
            }
        }
        return toReturn;
    }

    private EntryRessource parseStringToEntryRessource(String entryCSVInput) {
        String[] split = entryCSVInput.split(",");
        String lecture = split[0];
        String start = split[1];
        String end = split[2];
        String type = split[3];
        String details = split[4];
        String status = split[5];
        String id = split[6];
        return new EntryRessource(start, end, EntryRessourceType.valueOf(type), details, lecture, EntryRessourceStatus.valueOf(status), id);
    }
}
