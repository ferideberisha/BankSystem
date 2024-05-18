package com.banksystem;
	
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.SQLException;
	
	public class Transaction {
	    private double amount;
	    private String originatingAccountId;
	    private String resultingAccountId;
	    private String reason;
	
	    public Transaction(double amount, String originatingAccountId, String resultingAccountId, String reason) {
	        this.amount = amount;
	        this.originatingAccountId = originatingAccountId;
	        this.resultingAccountId = resultingAccountId;
	        this.reason = reason;
	    }
	
	    public void saveToDatabase() throws SQLException {
	        try (Connection conn = DatabaseConnection.getConnection().getConnectionInstance()) {
	            String sql = "INSERT INTO Transaction (amount, originatingAccountId, resultingAccountId, reason) VALUES (?, ?, ?, ?)";
	            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                pstmt.setDouble(1, getAmount());
	                pstmt.setString(2, originatingAccountId);
	                pstmt.setString(3, resultingAccountId);
	                pstmt.setString(4, reason);
	                pstmt.executeUpdate();
	            }
	        }
	    }
	
	    @Override
	    public String toString() {
	        return "Transaction [amount=$" + String.format("%.2f", getAmount()) + ", from=" + originatingAccountId + ", to=" + resultingAccountId + ", reason=" + reason + "]";
	    }
	
	    public double getAmount() {
	        return amount;
	    }
	    
	    public String getOriginatingAccountId() {
	        return originatingAccountId;
	    }
	    
	    public String getResultingAccountId() {
	        return resultingAccountId;
	    }
	}