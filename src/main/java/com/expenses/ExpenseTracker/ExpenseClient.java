package com.expenses.ExpenseTracker;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Scanner;

public class ExpenseClient
{

    public static void main(String[] args)
    {
        String username = "dhanush";
        String password = "qak076269#";
        //String requestBody = "{\"quantity\":10}";
        Scanner sc=new Scanner(System.in);

        int flag=0;
        while (true) {
            System.out.println("1. Get All Categories");
            System.out.println("2. Get Category By Id");
            System.out.println("3. Create New Category");
            System.out.println("4. Update Already Existing Category");
            System.out.println("5. Delete Already Existing Category");
            System.out.println("6. Get All Categories Budget");
            System.out.println("7. Update Spending's Here");
            System.out.println("8. Download History From Here");
            System.out.println("9. END");
            Byte value=sc.nextByte();
            switch (value) {
                case 1:
                    try {
                        String url1 = "http://localhost:9090/ExpenseTracker/v1.0/categories/all";
                        String response = sendGetRequest(url1, username, password);
                        System.out.println(response);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Exception occurred: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Enter category Id: ");
                    String url2 = "http://localhost:9090/ExpenseTracker/v1.0/categories/"+sc.next();
                    try {

                        String response = sendGetRequest(url2, username, password);
                        System.out.println(response);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Exception occurred: " + e.getMessage());
                    }
                    break;
                case 3:
                    String url3 = "http://localhost:9090/ExpenseTracker/v1.0/categories/create";
                    System.out.println("Enter categoryName: ");
                    String categoryName=sc.next();
                    System.out.println("Enter categoryBudget: ");
                    String categoryBudget=sc.next();


                    String requestBody3 = "{\"categoryName\": \""+categoryName+"\", \"categoryBudget\": "+categoryBudget+"}";
                    try {

                        String response = sendPostRequest(url3, requestBody3, username, password);
                        System.out.println(response);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Exception occurred: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Enter category Id: ");
                    String url4 = "http://localhost:9090/ExpenseTracker/v1.0/categories/update/"+sc.nextInt();
                    System.out.println("Enter categoryName: ");
                    String categoryName1=sc.next();
                    System.out.println("Enter categoryBudget: ");
                    int categoryBudget1=sc.nextInt();
                    String requestBody4 = "{\"categoryName\": \""+categoryName1+"\", \"categoryBudget\": "+categoryBudget1+"}";
                    try {
                        String response = sendPutRequest(url4, requestBody4, username, password);
                        System.out.println(response);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Exception occurred: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Enter category Id: ");
                    String url5 = "http://localhost:9090/ExpenseTracker/v1.0/categories/delete/"+sc.nextInt();

                    try {

                        String response = sendDeleteRequest(url5, username, password);
                        System.out.println(response);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Exception occurred: " + e.getMessage());
                    }
                    System.out.println("Deleted Successfully");
                    break;
                case 6:
                    String url6 = "http://localhost:9090/ExpenseTracker/v1.0/categories/CategoryAmount";

                    try {
                        String response = sendGetRequest(url6, username, password);
                        System.out.println(response);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Exception occurred: " + e.getMessage());
                    }
                    break;
                case 7:
                    String url7 = "http://localhost:9090/ExpenseTracker/v1.0/categories/spend";
                    System.out.println("Enter the categoryId :");
                    int categoryId= sc.nextInt();
                    System.out.println("Enter the spending :");
                    int categoryBudget3= sc.nextInt();
                    String requestBody7 = "{\"categoryId\": \""+categoryId+"\", \"categoryBudget\": "+categoryBudget3+"}";


                    try {
                        String response = sendPostRequest(url7, requestBody7, username, password);
                        System.out.println(response);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Exception occurred: " + e.getMessage());
                    }
                    break;
                case 8:
                    String url8 = "http://localhost:9090/ExpenseTracker/v1.0/categories/history";


                    try {
                        String response = sendGetRequest(url8, username, password);
                        System.out.println(response);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Exception occurred: " + e.getMessage());
                    }
                    break;
                case 9:
                    flag=1;
                    break;
                default:
                    break;

            }
            if(flag==1)
                break;
        }
    }

    private static String sendGetRequest(String urlString, String username, String password) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();

        String credentials = username + ":" + password;
        String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Authorization", authHeaderValue)
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return httpResponse.body();
    }

    private static String sendPostRequest(String urlString, String requestBody, String username, String password) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();

        String credentials = username + ":" + password;
        String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Authorization", authHeaderValue)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return httpResponse.body();
    }

    private static String sendPutRequest(String urlString, String requestBody, String username, String password) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();

        String credentials = username + ":" + password;
        String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Authorization", authHeaderValue)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return httpResponse.body();
    }

    private static String sendDeleteRequest(String urlString, String username, String password) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();

        String credentials = username + ":" + password;
        String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Authorization", authHeaderValue)
                .DELETE()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return httpResponse.body();
    }
}

